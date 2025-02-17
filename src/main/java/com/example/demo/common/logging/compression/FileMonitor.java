package com.example.demo.common.logging.compression;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.status.StatusUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class FileMonitor {
    private static final FileMonitor instance = new FileMonitor();
    private final Timer timer = new Timer(true);
    private final Hashtable<String, FileMonitorTask> timerEntries = new Hashtable();

    public static FileMonitor getInstance() {
        return instance;
    }

    private FileMonitor() {
    }

    public void startFileChangeListener(Context context, FileChangeListener listener, File file, long period) throws FileNotFoundException {
        this.addFileChangeListener(context, listener, file, period);
    }

    private void addFileChangeListener(Context context, FileChangeListener listener, File file, long period) throws FileNotFoundException {
        this.removeFileChangeListener(listener, file);
        FileMonitorTask task = new FileMonitorTask(context, listener, file);
        this.timerEntries.put(file.toString() + listener.hashCode(), task);
        this.timer.schedule(task, period, period);
    }

    public void removeFileChangeListener(FileChangeListener listener, File file) {
        FileMonitorTask task = (FileMonitorTask)this.timerEntries.remove(file.toString() + listener.hashCode());
        if (null != task) {
            task.cancel();
        }

    }

    protected void fireFileChangeEvent(FileChangeListener listener, File file) {
        listener.fileChanged(file);
    }

    class FileMonitorTask extends TimerTask {
        private final WeakReference<FileChangeListener> listener;
        private final File monitoredFile;
        private long lastModified;
        private final Context context;

        public FileMonitorTask(Context context, FileChangeListener listener, File file) throws FileNotFoundException {
            this.listener = new WeakReference(listener);
            this.lastModified = 0L;
            this.monitoredFile = file;
            this.context = context;
            if (!this.monitoredFile.exists()) {
                throw new FileNotFoundException("file not found " + this.monitoredFile.getPath());
            } else {
                this.lastModified = this.monitoredFile.lastModified();
            }
        }

        public void run() {
            FileChangeListener fileChangeListener = (FileChangeListener)this.listener.get();
            if (null != fileChangeListener) {
                StatusUtil statusUtil = new StatusUtil(this.context);
                long lastModified = this.monitoredFile.lastModified();
                if (lastModified != this.lastModified) {
                    this.lastModified = lastModified;
                    FileMonitor.this.fireFileChangeEvent(fileChangeListener, this.monitoredFile);
                    statusUtil.addInfo(this, "file content changed:" + this.monitoredFile.getAbsolutePath());
                }

                statusUtil.addInfo(this, "file monitor:" + this.monitoredFile.getAbsolutePath());
            }
        }
    }
}
