package com.example.demo.common.logging;

import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.helper.*;
import ch.qos.logback.core.util.FileSize;
import com.example.demo.common.logging.compression.FileChangeListener;
import com.example.demo.common.logging.compression.FileMonitor;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TimeBasedRollingPolicy<E> extends SizeAndTimeBasedRollingPolicy<E> implements FileChangeListener {
    private final BlockingQueue<Future<?>> compressionFutureList = new ArrayBlockingQueue(10);
    private FileSize totalFileSize = new FileSize(0L);
    private final int timeout = 30;
    private int compressionLazyCount;
    private boolean isCompression;
    private boolean usingDefaultMaxFileSize = true;
    private boolean usingDefaultTotalSizeCap = true;
    private boolean usingDefaultMaxHistory = true;
    private boolean usingDefaultCompression = true;
    private boolean usingDefaultCompressionLazyCount = true;
    private FileSize maxFileSize;

    public TimeBasedRollingPolicy() {
    }

    private void compressFile(File file) {
        Compressor compressor = new Compressor(CompressionMode.GZ);
        compressor.setContext(this.context);
        Future<?> compressionFuture = compressor.asyncCompress(file.getAbsolutePath(), file.getAbsolutePath(), file.getName());
        if (!this.compressionFutureList.offer(compressionFuture)) {
            this.checkFuture(timeout);
            boolean success = this.compressionFutureList.offer(compressionFuture);
            if (!success) {
                this.addError("Empty array block queue fail");
            }
        }

    }

    public long getFileOrder(String fileName) {
        DateTokenConverter<Object> datePattern = (new FileNamePattern(this.fileNamePatternStr, this.context)).getPrimaryDateTokenConverter();
        long startValue = 0L;
        if (null != datePattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern.getDatePattern());
            String[] fileArray = fileName.split("\\.");
            if (fileArray.length > 1) {
                String dateString = fileArray[1];

                try {
                    Date date = simpleDateFormat.parse(dateString);
                    startValue += date.getTime();
                } catch (ParseException var9) {
                    this.addWarn("date format not match, " + datePattern.getDatePattern());
                }
            }
        }

        String regFileName = "(\\d+).log";
        Matcher matcher = Pattern.compile(regFileName).matcher(fileName);
        return matcher.find() ? startValue + Long.parseLong(matcher.group().replace(".log", "")) : 0L;
    }

    private boolean isEnvironmentReady() {
        return !this.context.getProperty("log.name").equals("localhost.log");
    }

    private void checkAndCompressFile() {
        if (this.isCompression) {
            this.addInfo("file compression is open");
            if (this.isEnvironmentReady()) {
                this.checkFuture(30);
                int keepItem = this.compressionLazyCount;
                List<File> fileList = this.getHistoryLogs(false);
                List<File> historyFile;
                if (fileList.size() > keepItem) {
                    historyFile = fileList.subList(0, fileList.size() - keepItem);
                    historyFile.forEach(this::compressFile);
                }

                historyFile = this.getHistoryLogs(true);
                this.cleanByTotalSize();
                this.cleanZipFile(historyFile);
            }
        } else {
            this.addInfo("file compression is close");
        }
    }

    public void setTotalSizeCap(FileSize totalSizeCap) {
        super.setTotalSizeCap(totalSizeCap);
        this.totalFileSize = totalSizeCap;
        this.usingDefaultTotalSizeCap = false;
    }

    private void cleanZipFile(List<File> sortedFile) {
        if (sortedFile.size() > this.getMaxHistory()) {
            int count = sortedFile.size() - this.getMaxHistory();
            List<File> gzFile = sortedFile.subList(0, count);
            Iterator var4 = gzFile.iterator();

            while(var4.hasNext()) {
                File file = (File)var4.next();
                if (file.exists()) {
                    String fileName = file.getName();
                    boolean success = file.delete();
                    this.addInfo(String.format("CB policy [name:clean zip file][gz_file=%s, success=%s]", fileName, success));
                }
            }
        }

    }

    private void cleanByTotalSize() {
        long totalSize = 0L;
        long totalRemoved = 0L;
        List<File> sortedFile = this.getHistoryLogs(true);
        Collections.reverse(sortedFile);
        if (this.totalFileSize.getSize() != 0L && this.totalFileSize.getSize() > 0L) {
            long size;
            for(Iterator var6 = sortedFile.iterator(); var6.hasNext(); totalSize += size) {
                File file = (File)var6.next();
                size = file.length();
                if (totalSize + size > this.totalFileSize.getSize()) {
                    this.addInfo("Deleting [" + file.getPath() + "] of size " + new FileSize(size));
                    totalRemoved += size;
                    if (file.exists()) {
                        boolean success = file.delete();
                        this.addInfo("CB policy [name:clean by total size][totalSize=" + (totalSize + size) / 1024L + "KB, file=" + file.getName() + ", success=" + success + "]");
                    }
                }
            }

            this.addInfo("Removed  " + new FileSize(totalRemoved) + " of files");
        }

    }

    public void checkFuture(int timeout) {
        Future task = null;

        while((task = (Future)this.compressionFutureList.poll()) != null) {
            try {
                long startTime = System.currentTimeMillis();
                this.addInfo("CB policy [name:wait until compression finished][task_isDone=" + task.isDone() + "]");
                task.get((long)timeout, TimeUnit.SECONDS);
                long endTime = System.currentTimeMillis();
                this.addInfo("CB policy [name:wait until compression finished][task_isDone=" + task.isDone() + ", cost=" + (endTime - startTime) / 1000L + "s]");
            } catch (InterruptedException var7) {
                this.addError("InterruptedException for compression job to finish", var7);
            } catch (ExecutionException var8) {
                this.addError("ExecutionException for compression job to finish", var8);
            } catch (TimeoutException var9) {
                this.addError("TimeoutException for compression job to finish", var9);
            }
        }

    }

    private String escapeRightPathSeparator(String in) {
        return in.replace("\\", "/");
    }

    private List<File> getHistoryLogs(boolean all) {
        File activeFile = new File(this.getActiveFileName());
        String directory = activeFile.getParent();
        File dir = new File(directory);
        DateTokenConverter<Object> datePattern = (new FileNamePattern(this.fileNamePatternStr, this.context)).getPrimaryDateTokenConverter();
        if (datePattern != null) {
            String regex = datePattern.toRegex() + ".\\d+.(log)(.gz)?$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(activeFile.getPath());
            if (!m.find()) {
                return new ArrayList(0);
            }

            String fileRegex = this.escapeRightPathSeparator(activeFile.getPath().replaceAll("." + m.group(), ""));
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles((pathname) -> {
                    if (p.matcher(pathname.getPath()).find() && !pathname.isDirectory()) {
                        if (all) {
                            return this.escapeRightPathSeparator(pathname.getPath()).contains(fileRegex);
                        } else {
                            return this.escapeRightPathSeparator(pathname.getPath()).contains(fileRegex) && !pathname.getPath().contains(".gz");
                        }
                    } else {
                        return false;
                    }
                });
                if (null != files) {
                    return (List)Arrays.stream(files).sorted(Comparator.comparingLong((o) -> {
                        return this.getFileOrder(o.getName());
                    })).collect(Collectors.toList());
                }
            }
        }

        return new ArrayList(0);
    }

    public void start() {
        this.setCustomProperties();
        super.start();
        this.checkAndCompressFile();
    }

    private void setCustomProperties() {
        String compression = this.context.getProperty("log.compression");
        String compressionLazyCount = this.context.getProperty("log.compression.lazyCount");
        if (StringUtils.isBlank(compression)) {
            this.isCompression = true;
        } else {
            this.setCompression(compression, true);
        }

        if (StringUtils.isBlank(compressionLazyCount)) {
            this.compressionLazyCount = 1;
        } else {
            this.setCompressionLazyCount(compressionLazyCount, true);
        }

        if (this.usingDefaultMaxFileSize) {
            this.maxFileSize = FileSize.valueOf("50MB");
            super.setMaxFileSize(this.maxFileSize);
        }

        if (this.usingDefaultTotalSizeCap) {
            this.totalFileSize = FileSize.valueOf("250MB");
            super.setTotalSizeCap(this.totalSizeCap);
        }

        if (this.usingDefaultMaxHistory) {
            super.setMaxHistory(7);
        }

        if (this.isEnvironmentReady()) {
            File policyConfigFile = new File("/opt/com/reptile/logs/compression_log.properties");
            if (!policyConfigFile.exists()) {
                File dir = new File(policyConfigFile.getParent());
                if (!dir.exists() && !dir.mkdirs()) {
                    this.addError("create dir fail, " + dir.getAbsolutePath());
                }

                this.initDefaultFile(policyConfigFile);
            }

            if (policyConfigFile.exists()) {
                try {
                    this.fileChanged(policyConfigFile);
                    FileMonitor.getInstance().startFileChangeListener(this.context, this, policyConfigFile, 60000L);
                    this.addInfo("start listener:" + this.getFileNamePattern());
                } catch (FileNotFoundException var5) {
                    this.addError("file not found, path " + policyConfigFile.getAbsolutePath());
                }
            }
        }

    }

    private void setCompression(String compression, boolean changeDefaultConfig) {
        if (!compression.equals("true") && !compression.equals("false")) {
            this.addError("compression value is wrong. please set value  true or false");
        }

        this.isCompression = compression.equals("true");
        if (changeDefaultConfig) {
            this.usingDefaultCompression = false;
        }

    }

    private void setCompressionLazyCount(String compressionLazyCount, boolean changeDefaultConfig) {
        if (this.isNumeric(compressionLazyCount)) {
            int count = Integer.parseInt(compressionLazyCount);
            if (count >= 1) {
                this.compressionLazyCount = count;
                if (changeDefaultConfig) {
                    this.usingDefaultCompressionLazyCount = false;
                }
            }
        } else {
            this.addError("compression lazy count need numeric value . please set numeric value");
        }

    }

    private boolean isNumeric(String value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher isNum = pattern.matcher(value);
        return isNum.matches();
    }

    public void rollover() throws RolloverFailure {
        super.rollover();
        this.checkAndCompressFile();
    }

    public void stop() {
        this.addInfo("stop policy, file name pattern:" + this.fileNamePatternStr);
        if (this.isStarted()) {
            this.checkFuture(30);
            File policyConfigFile = new File("/opt/reptile-data-platform/compression_log.properties");
            FileMonitor.getInstance().removeFileChangeListener(this, policyConfigFile);
            super.stop();
        }

    }

    public void setFileNamePattern(String fnp) {
        FileNamePattern fileNamePattern = new FileNamePattern(fnp, this.context);
        IntegerTokenConverter itc = fileNamePattern.getIntegerTokenConverter();
        if (null == itc) {
            String datePattern = fileNamePattern.getPrimaryDateTokenConverter().getDatePattern();
            this.fileNamePatternStr = fnp.replaceAll("%d\\{" + datePattern + "\\}", "%d{" + datePattern + "}.%i");
        } else {
            this.fileNamePatternStr = fnp;
        }

    }

    public void setMaxFileSize(FileSize aMaxFileSize) {
        super.setMaxFileSize(aMaxFileSize);
        this.maxFileSize = aMaxFileSize;
        this.usingDefaultMaxFileSize = false;
    }

    public void setMaxHistory(int maxHistory) {
        super.setMaxHistory(maxHistory);
        this.usingDefaultMaxHistory = false;
    }

    public void fileChanged(File file) {
        if (file.exists()) {
            Properties properties = new Properties();

            try {
                InputStream stream = Files.newInputStream(file.toPath());
                Throwable var4 = null;

                try {
                    properties.load(stream);
                    String enableConfig = properties.getProperty("enableConfig");
                    if (StringUtils.isNotBlank(enableConfig)) {
                        if (!enableConfig.equals("true") && !enableConfig.equals("false")) {
                            this.addWarn("enable config value is wrong. please set value true or false");
                            return;
                        }

                        if (enableConfig.equals("false")) {
                            return;
                        }
                    }

                    String maxHistory = properties.getProperty("maxHistory");
                    String maxFileSize = properties.getProperty("maxFileSize");
                    String totalSizeCap = properties.getProperty("totalSizeCap");
                    String compression = properties.getProperty("compression");
                    String lazyCount = properties.getProperty("lazyCount");
                    if (StringUtils.isNotBlank(maxHistory) && this.usingDefaultMaxHistory) {
                        if (this.isNumeric(maxHistory)) {
                            super.setMaxHistory(Integer.parseInt(maxHistory));
                            this.syncMaxHistory();
                        } else {
                            this.addWarn("max history should be numeric");
                        }
                    }

                    if (StringUtils.isNotBlank(maxFileSize) && this.usingDefaultMaxFileSize) {
                        try {
                            this.maxFileSize = FileSize.valueOf(maxFileSize);
                            super.setMaxFileSize(this.maxFileSize);
                            this.syncMaxFileSize();
                        } catch (IllegalArgumentException var25) {
                            this.addWarn("max file size value wrong, " + var25.getMessage());
                        }
                    }

                    if (StringUtils.isNotBlank(totalSizeCap) && this.usingDefaultTotalSizeCap) {
                        try {
                            this.totalFileSize = FileSize.valueOf(totalSizeCap);
                            super.setTotalSizeCap(this.totalFileSize);
                            this.syncTotalSizCap();
                        } catch (IllegalArgumentException var24) {
                            this.addWarn("total size cap value wrong, " + var24.getMessage());
                        }
                    }

                    if (StringUtils.isNotBlank(compression) && this.usingDefaultCompression) {
                        this.setCompression(compression, false);
                    }

                    if (StringUtils.isNotBlank(lazyCount) && this.usingDefaultCompressionLazyCount) {
                        this.setCompressionLazyCount(lazyCount, false);
                    }

                } catch (Throwable var26) {
                    var4 = var26;
                    throw var26;
                } finally {
                    if (stream != null) {
                        if (var4 != null) {
                            try {
                                stream.close();
                            } catch (Throwable var23) {
                                var4.addSuppressed(var23);
                            }
                        } else {
                            stream.close();
                        }
                    }

                }
            } catch (IOException var28) {
                throw new RuntimeException(var28);
            }
        }
    }

    protected void syncMaxFileSize() {
        if (null != this.getTimeBasedFileNamingAndTriggeringPolicy()) {
            TimeBasedFileNamingAndTriggeringPolicy<E> triggeringPolicy = this.getTimeBasedFileNamingAndTriggeringPolicy();
            if (triggeringPolicy instanceof SizeAndTimeBasedFNATP) {
                if (!this.isUnboundedTotalSizeCap() && this.totalSizeCap.getSize() < this.maxFileSize.getSize()) {
                    this.addWarn("totalSizeCap of [" + this.totalSizeCap + "] is smaller than maxFileSize [" + this.maxFileSize + "] which is non-sensical");
                    return;
                }

                SizeAndTimeBasedFNATP<E> sizeAndTimeBasedFNATP = (SizeAndTimeBasedFNATP)triggeringPolicy;
                sizeAndTimeBasedFNATP.setMaxFileSize(this.maxFileSize);
            }

        }
    }

    protected void syncTotalSizCap() {
        if (null != this.getTimeBasedFileNamingAndTriggeringPolicy()) {
            this.getTimeBasedFileNamingAndTriggeringPolicy().getArchiveRemover().setTotalSizeCap(this.totalFileSize.getSize());
        }
    }

    protected void syncMaxHistory() {
        if (null != this.getTimeBasedFileNamingAndTriggeringPolicy()) {
            this.getTimeBasedFileNamingAndTriggeringPolicy().getArchiveRemover().setMaxHistory(this.getMaxHistory());
        }
    }

    private void initDefaultFile(File policyConfigFile) {
        if (!policyConfigFile.exists()) {
            Properties properties = new Properties();

            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(policyConfigFile.toPath());
                Throwable var4 = null;

                try {
                    properties.setProperty("enableConfig", String.valueOf(false));
                    properties.setProperty("maxHistory", String.valueOf(7));
                    properties.setProperty("maxFileSize", "50MB");
                    properties.setProperty("totalSizeCap", "250MB");
                    properties.setProperty("compression", String.valueOf(true));
                    properties.setProperty("lazyCount", String.valueOf(1));
                    properties.store(bufferedWriter, "init default compression file");
                } catch (Throwable var14) {
                    var4 = var14;
                    throw var14;
                } finally {
                    if (bufferedWriter != null) {
                        if (var4 != null) {
                            try {
                                bufferedWriter.close();
                            } catch (Throwable var13) {
                                var4.addSuppressed(var13);
                            }
                        } else {
                            bufferedWriter.close();
                        }
                    }

                }

            } catch (IOException var16) {
                throw new RuntimeException(var16);
            }
        }
    }
}
