package com.example.demo.task;

import cn.hutool.core.io.FileUtil;
import com.example.demo.task.util.RestTemplateConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Slf4j
//@Component
public class OtherTask {
    @Scheduled(cron = "0 0 6 * * ?")
    //@Scheduled(cron = "0/2 * * * * ?")
    public void backupFile() throws IOException {
        log.info("备份文件");
        String host = "http://101.200.56.62";
        String port = "8080";
        String downloadUrl = "/reptile-data-platform/system/upload-backup-file";
        String confirmUrl = "/reptile-data-platform/system/delete-backup-file";
        String filePath = "/data/backup/";

        FileUtil.mkdir(filePath);
        // 接收备份文件，如果没有停止
        RestTemplate restTemplate = RestTemplateConf.restTemplate();
        while(true) {
            ResponseEntity<Resource> backupFileResponse = restTemplate.getForEntity(host + ":" + port + downloadUrl, Resource.class);
            Resource body = backupFileResponse.getBody();
            if(body == null) {
                log.info("没有备份文件");
                break;
            } else {
                log.info("备份文件：" + body.getFilename());
                File file = FileUtil.writeFromStream(body.getInputStream(), filePath + body.getFilename());
                if (file.exists()) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.TEXT_PLAIN);

                    HttpEntity<String> entity = new HttpEntity<>(body.getFilename(), headers);
                    log.info("删除备份文件：" + body.getFilename());
                    restTemplate.exchange(host + ":" + port + confirmUrl, HttpMethod.DELETE, entity, String.class);
                }
                log.info("备份文件成功");
            }
        }
    }
}
