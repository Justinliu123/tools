package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class KeyCountController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
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

    @RequestMapping("/txtToHtml")
    public String txtToHtml(@RequestParam("filePath") String filePath) {
        File[] ls = FileUtil.ls(filePath);
        for (File file : ls) {
            if(FileUtil.extName(file).equals("txt")) {
                FileUtil.rename(file, file.getName().replace(".txt", ".html"), true);
            }
        }
        return "success";
    }

    /**
     * 查询文件夹下的所有txt文件，搜索关键字，搜索到的文件，将文件路径存储在 “/data/searchFile.txt” 文件中
     *
     * @param filePath 文件路径
     * @param key      钥匙
     * @return {@link String }
     */
    @RequestMapping("/searchKey")
    public String searchKey(@RequestParam("filePath") String filePath, @RequestParam("key") String key,
                            @RequestParam(value = "industry", required = false) String industry) {
        String resultFilePath = "/data/searchFile_"+ key + ".txt";
        File file = new File(resultFilePath);
        if(file.exists()) {
            return "文件已存在";
        }
        FileUtil.touch(resultFilePath);
        StringBuilder result = new StringBuilder();

        // 遍历filePath下的所有txt路径
        List<String> allPath = new ArrayList<>();
        getTxtFile(new File(filePath), allPath, industry);

        log.info("===============================共{}个文件需要对比", allPath.size());

        // 遍历所有txt文件，搜索关键字，将搜索到的文件路径存储在result中
        for (String path : allPath) {
            String fileContent = FileUtil.readUtf8String(path);
            fileContent = HtmlUtil.unescape(
                    StringUtils.trimAllWhitespace(
                            HtmlUtil.cleanHtmlTag(
                                    HtmlUtil.removeHtmlTag(fileContent, "style"))));
            if (fileContent.contains(key)) {
                result.append(path).append("\r\n");
                FileUtil.appendUtf8String(path, file);
                FileUtil.appendUtf8String("\n", file);
                log.info("================当前文档{}符合条件================", path);
            }
            log.info("{}不符合条件=====", path);
        }

        return result.toString();
    }

    // 递归获取路径下的所有txt文件
    public void getTxtFile(File file, List<String> allPath, String directoryName) {
        if (file.isDirectory()) {
            log.info("======================当前搜索目录：{}======================", file.getPath());
            File[] files = file.listFiles();
            if(files == null) return;
            // 查询文件夹名称是否有等于directoryName的
            boolean flag = false;
            for (File file1 : files) {
                if(!StringUtils.hasLength(directoryName)) break;
                if (file1.getName().equals(directoryName)) {
                    flag = true;
                }
            }
            if(StringUtils.hasLength(directoryName) && flag) {
                for (File file1 : files) {
                    if(file1.getName().equals(directoryName)) {
                        getTxtFile(file1, allPath, directoryName);
                    }
                }
            } else {
                for (File file1 : files) {
                    getTxtFile(file1, allPath, directoryName);
                }
            }
        } else {
            if (file.getName().endsWith(".txt")) {
                allPath.add(file.getPath());
            }
        }
    }
}
