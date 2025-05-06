package com.example.demo.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;

@Slf4j
public class FileUtils {
    /**
     * 递归解压所有文件
     *
     * @param destFiles 目标文件
     * @return {@link File} 解压后的最外层file
     */
    public static File unzipByGBK(String destFiles, String targetDir) {
        File file = ZipUtil.unzip(destFiles, targetDir, CharsetUtil.CHARSET_GBK);
        File[] list = file.listFiles(name -> name.getName().contains(".zip"));
        if (list != null) {
            Arrays.stream(list).forEach(item -> unzipByGBK(item.getPath(), targetDir));
        }
        return file;
    }
}
