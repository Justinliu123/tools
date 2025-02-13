package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class ClearDataTask {
    // 每天零点执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void dataClear(){
        // 清除1月前数据
    }
}
