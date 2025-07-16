package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.AnnouncementDto;
import com.example.demo.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class KeyCountController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }


    @PostMapping("/receive-backup-file")
    public String receiveBackupFile(HttpServletRequest request) throws IOException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (MultipartFile multipartFile : files) {
            FileUtil.writeBytes(multipartFile.getBytes(), "/data/backup/" + multipartFile.getOriginalFilename());
        }
        return "success";
    }

    /**
     * 解压缩文件夹下的所有压缩文件。会保留zip名称
     *
     * @return {@link String }
     */
    @RequestMapping("/unZipFiles")
    public String unZipFiles() {
        String path = "D:\\tmp\\work";
        String target = "D:\\tmp\\work\\unzip";
        // 获取文件夹下的所有zip文件
        List<File> files = FileUtil.loopFiles(path, file -> file.getName().endsWith(".zip"));
        for (File file : files) {
            String fileMainName = FileUtil.mainName(file);
            try {
                ZipUtil.unzip(file.getPath(), target + "/" + fileMainName);
            } catch (Exception e) {
                log.error("解压失败：" + fileMainName);
                continue;
            }
            log.info("解压成功：" + fileMainName);
        }
        return "success";
    }

    /**
     * 复制文件，将指定txt的每一行作为目标路径，复制到目标文件夹下
     *
     * @param filePath   文件路径
     * @param targetPath 目标路径
     * @return {@link String }
     */
    @RequestMapping("/copyFile")
    public String copyFile(@RequestParam("filePath") String filePath, @RequestParam("targetPath") String targetPath) {
        FileUtil.mkdir(targetPath);
        List<String> filePaths = FileUtil.readLines(filePath, StandardCharsets.UTF_8);
        for (String path : filePaths) {
            if (!StringUtils.hasLength(path)) {
                continue;
            }
            FileUtil.copy(path, targetPath, true);
        }
        return "success";
    }

    /**
     * 将文件夹下的txt转换为html
     *
     * @param filePath 文件路径
     * @return {@link String }
     */
    @RequestMapping("/txtToHtml")
    public String txtToHtml(@RequestParam("filePath") String filePath) {
        File[] ls = FileUtil.ls(filePath);
        for (File file : ls) {
            if (FileUtil.extName(file).equals("txt")) {
                FileUtil.rename(file, file.getName().replace(".txt", ".html"), true);
            }
        }
        return "success";
    }

    /**
     * 查询文件夹下的所有txt文件，搜索关键字，搜索到的文件，将文件路径存储在 “/data/searchFile.csv” 文件中;
     * 将搜索到的相关数据提取出来，保存在“/data/searchFile.csv中”
     *
     * @param key      钥匙
     * @return {@link String }
     */
    @RequestMapping("/searchKey")
    public String searchKey(@RequestParam("key") String key,
                            @RequestParam(value = "industry", required = false) String industry) {
        String filePath = "D:\\tmp\\work\\unzip";
        String resultFilePath = "/data/searchFile_" + key + ".csv";
        // 写入标题
        File file = new File(resultFilePath);
        if (file.exists()) {
            return "文件已存在";
        }
        FileUtil.touch(resultFilePath);
        FileUtil.appendUtf8String("项目标号,站点编号,公告名称,文件路径,公告发布时间,公告链接,所属城市\r\n", file);
        StringBuilder result = new StringBuilder();

        // 遍历filePath下的所有txt路径
        List<String> allPath = new ArrayList<>();
        getTxtFile(new File(filePath), allPath, industry);

        log.info("===============================共{}个文件需要对比", allPath.size());

        // 遍历所有txt文件，搜索关键字
        for (String path : allPath) {
            String fileContent = FileUtil.readUtf8String(path);
            fileContent = HtmlUtil.unescape(
                    StringUtils.trimAllWhitespace(
                            HtmlUtil.cleanHtmlTag(
                                    HtmlUtil.removeHtmlTag(fileContent, "style"))));
            if (fileContent.contains(key)) {
                result.append(path).append("\r\n");
                log.info("================当前文档{}符合条件================", path);
                // 在文件的上一级目录里面获取announcements.csv文件，并提取出相关数据，保存在resultFilePath 的csv中
                String parent = FileUtil.getParent(path, 2);
                File announcementsFile = new File(parent + "/announcements.csv");
                if(!announcementsFile.exists()) {
                    log.info("{}不存在", announcementsFile.getPath());
                    continue;
                }
                CsvReader reader = CsvUtil.getReader();
                List<AnnouncementDto> announcementDtos = reader.read(FileUtil.getReader(announcementsFile, CharsetUtil.UTF_8), AnnouncementDto.class);
                Map<String, AnnouncementDto> numberAnnouncementMap = announcementDtos.stream()
                        .collect(Collectors.toMap(AnnouncementDto::getItemNumber, Function.identity()));
                String mainName = FileNameUtil.getPrefix(path);
                AnnouncementDto announcementDto = numberAnnouncementMap.get(mainName);
                if(announcementDto == null) {
                    announcementDto = numberAnnouncementMap.get(mainName.substring(0, mainName.indexOf("_")));
                }
                // 写入相关信息到resultFilePath 的csv中
                if (announcementDto != null) {
                    StringBuilder csvString = new StringBuilder();
                    csvString.append(announcementDto.getItemNumber()).append(",")
                            .append(announcementDto.getAddressId()).append(",")
                            .append(announcementDto.getAnnouncementName()).append(",")
                            .append(path).append(",")
                            .append(announcementDto.getPublishTime()).append(",")
                            .append(announcementDto.getNoticeUrl()).append(",")
                            .append(announcementDto.getCity()).append(",");
                    csvString.append("\r\n");
                    FileUtil.appendUtf8String(csvString.toString(), file);
                    log.info("================当前文档{}写入一条数据: {}================", path, csvString.toString());
                } else {
                    log.error("announcements.csv中没有找到对应的公告:{}", path);
                }
            }
            log.info("{}不符合条件=====", path);
        }

        return result.toString();
    }

    /**
     * 获取txt文件；这是将每个zip单独解压的
     *
     * @param file     文件
     * @param allPath  所有路径
     * @param industry 行业
     */
    private void getTxtFile(File file, List<String> allPath, String industry) {
        if (file.isDirectory()) {
            // 获取文件夹下的所有文件夹，进入
            File[] files = file.listFiles();
            if (files == null) return;
            for (File file1 : files) {
                getTxtFileTogether(file1, allPath, industry);
            }
        }
    }

    /**
     * 递归获取路径下的所有txt文件；这些txt，是将zip解压到一起的
     *
     * @param file          文件
     * @param allPath       所有路径
     * @param directoryName 目录名称
     */
    public void getTxtFileTogether(File file, List<String> allPath, String directoryName) {
        if (file.isDirectory()) {
            log.info("======================当前搜索目录：{}======================", file.getPath());
            File[] files = file.listFiles();
            if (files == null) return;
            // 查询文件夹名称是否有等于directoryName的
            boolean flag = false;
            for (File file1 : files) {
                if (!StringUtils.hasLength(directoryName)) break;
                if (file1.getName().equals(directoryName)) {
                    flag = true;
                }
            }
            if (StringUtils.hasLength(directoryName) && flag) {
                for (File file1 : files) {
                    if (file1.getName().equals(directoryName)) {
                        getTxtFileTogether(file1, allPath, directoryName);
                    }
                }
            } else {
                for (File file1 : files) {
                    getTxtFileTogether(file1, allPath, directoryName);
                }
            }
        } else {
            if (file.getName().endsWith(".txt")) {
                allPath.add(file.getPath());
            }
        }
    }

    /**
     * 将指定日期范围的zip文件解压缩
     * 请求示例：http://172.17.24.34:5000/unZip?startDate=2023-08-28&endDate=2025-05-06&addressIds=1,2&type=%E6%9C%8D%E5%8A%A1
     */
    @GetMapping("/unZip")
    public String unZipFilesByDateRange(@RequestParam("startDate") LocalDate startDate,
                                        @RequestParam("endDate") LocalDate endDate,
                                        @RequestParam(value = "addressIds", required = false) List<Integer> addressIds,
                                        @RequestParam(value = "type", required = false) String type) {
        String zipPath = "/data/backup/";
        String target = "/data/temp/";
        String excelPathStr = "/data/result/announcements_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx";
        ExcelWriter excelWriter = ExcelUtil.getWriter(excelPathStr);
        excelWriter.addHeaderAlias("announcementName", "公告名称");
        excelWriter.addHeaderAlias("noticeUrl", "公告链接");
        excelWriter.addHeaderAlias("publishTime", "公告发布时间");
        excelWriter.addHeaderAlias("openTime", "开标时间");
        excelWriter.addHeaderAlias("type", "采购类型");
        excelWriter.addHeaderAlias("labels", "标签");
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            // announcements-backup-20250108.zip
            String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String unZipPathStr = target + dateStr;
            // /electricity/announcements.csv 如果已经解压过则无需解压
            String announcementsFilePath = unZipPathStr + "/electricity/announcements.csv";
            if(!FileUtil.exist(announcementsFilePath)) {
                String filePath = zipPath + "announcements-backup-" + dateStr + ".zip";
                if(!FileUtil.exist(filePath)) {
                    log.info("{}不存在", filePath);
                    continue;
                }
                try {
                    ZipUtil.unzip(filePath, unZipPathStr);
                } catch (Exception e) {
                    log.error("解压失败：" + filePath);
                    continue;
                }
            }
            if(!FileUtil.exist(announcementsFilePath)) {
                log.info("{}不存在", announcementsFilePath);
                continue;
            }
            // 读取announcements.csv文件
            CsvReader reader = CsvUtil.getReader();
            List<AnnouncementDto> announcementDtos = reader.read(FileUtil.getReader(announcementsFilePath, StandardCharsets.UTF_8), AnnouncementDto.class);
            // 过滤addressId 是参数addressId的 并且 labels可以匹配到label正则的、
            List<AnnouncementDto> filterAnnouncementDtos = announcementDtos.stream().filter(announcementDto -> {
                if(addressIds != null && !addressIds.contains(announcementDto.getAddressId())) {
                    return false;
                }
                if(StringUtils.hasLength(type) && !announcementDto.getType().matches(type)) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());
            // 将filterAnnouncementDtos写入excel excelPathStr
            log.info("{}写入数据{}",announcementsFilePath, filterAnnouncementDtos);
            excelWriter.setCurrentRowToEnd();
            excelWriter.write(filterAnnouncementDtos);
        }
        excelWriter.close();
        return "success";
    }

}
