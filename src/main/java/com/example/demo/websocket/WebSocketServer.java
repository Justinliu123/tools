package com.example.demo.websocket;

import cn.hutool.core.date.DateTime;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.UserDto;
import com.example.demo.websocket.encoder.ServerEncoder;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.Semaphore;

/**
 * websocket 消息处理
 *
 * @author ruoyi
 */
@Component
@ServerEndpoint(value = "/websocket/message/{chartNum}", encoders = {ServerEncoder.class})
public class WebSocketServer {
    @PostConstruct
    public void init() {
        LOGGER.info("WebSocketServer initialization completed");
    }
    /**
     * WebSocketServer 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 默认最多允许同时在线客户端
     */
    public static int socketMaxOnlineCount = 999;

    private static Semaphore socketSemaphore = new Semaphore(socketMaxOnlineCount);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("chartNum") String chartNum) throws Exception {
        System.out.println(String.format("%s", chartNum));
        boolean semaphoreFlag = false;
        // 尝试获取信号量
        semaphoreFlag = SemaphoreUtils.tryAcquire(socketSemaphore);
        if (!semaphoreFlag) {
            // 未获取到信号量
            LOGGER.error("当前在线人数超过限制数- {}", socketMaxOnlineCount);
            WebSocketUsers.sendMessageToUserByText(session, "当前在线人数超过限制数：" + socketMaxOnlineCount);
            session.close();
        } else {
            // 添加用户
            WebSocketUsers.put(session.getId(), chartNum, session);
            LOGGER.info("chartNum为 {} 的建立连接 {}, 建立连接时间 {}, 当前客户端数 {}",
                    chartNum,
                    session.getId(),
                    DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),
                    WebSocketUsers.getUsers().size());
        }
    }

    /**
     * 连接关闭时处理
     */
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("客户端连接关闭连接 {}, 断开时间 {}", session.getId(), DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        // 移除用户
        WebSocketUsers.remove(session.getId());
        // 获取到信号量则需释放
        SemaphoreUtils.release(socketSemaphore);
    }

    /**
     * 抛出异常时处理
     */
    @OnError
    public void onError(Session session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            // 关闭连接
            session.close();
        }
        String sessionId = session.getId();
        LOGGER.error("{}连接异常", sessionId);
        LOGGER.error("异常信息", exception);
        // 移出用户
        WebSocketUsers.remove(sessionId);
        // 获取到信号量则需释放
        SemaphoreUtils.release(socketSemaphore);
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // String msg = message.replace("你", "我").replace("吗", "");
        WebSocketUsers.sendMessageToUserByText(session, "连接保持，该服务不接收数据");
    }
}
