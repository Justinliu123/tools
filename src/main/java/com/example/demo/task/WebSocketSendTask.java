package com.example.demo.task;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserDto;
import com.example.demo.websocket.WebSocketUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class WebSocketSendTask {
    // @Scheduled(cron = "* * * * * *")
    public void sendMessage(){
        UserDto userDto = new UserDto();
        userDto.setNickName("名称");
        WebSocketUsers.sendMessageToUsersByText(JSON.toJSONString(userDto));
    }
}
