package com.example.demo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * websocket 客户端用户集
 *
 * @author ruoyi
 */
public class WebSocketUsers {
    static class ChartNumUser {
        private String key;
        private Session session;
        private String getKey() {
            return key;
        }
        private Session getSession() {
            return session;
        }
        ChartNumUser(String key, Session session) {
            this.key = key;
            this.session = session;
        }
    }
    /**
     * WebSocketUsers 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUsers.class);

    /**
     * 用户集
     */
    private static Map<String, ChartNumUser> USERS = new ConcurrentHashMap<>();

    // 用户通过CharNum分类的记录
    private static Map<String, List<Session>> ChartNumSessionsMap = new ConcurrentHashMap<>();

    /**
     * 存储用户
     *
     * @param key     唯一键
     * @param session 用户信息
     */
    public static void put(String key, String chartNum, Session session) {
        USERS.put(key, new ChartNumUser(chartNum, session));
        List<Session> orDefault = ChartNumSessionsMap.getOrDefault(chartNum, new ArrayList<>());
        orDefault.add(session);
        ChartNumSessionsMap.put(chartNum, orDefault);
    }

    /**
     * 移出用户
     *
     * @param key 键
     */
    public static boolean remove(String key) {
        ChartNumUser remove = USERS.remove(key);
        if (remove != null) {
            ChartNumSessionsMap.get(remove.getKey()).remove(remove.getSession());
            return !USERS.containsValue(remove);
        } else {
            return true;
        }
    }

    /**
     * 获取在线用户列表
     *
     * @return 返回用户集合
     */
    public static Map<String, ChartNumUser> getUsers() {
        return USERS;
    }
    
    public static boolean hasChartSession(String chartNum) {
        return ChartNumSessionsMap.get(chartNum) != null && !ChartNumSessionsMap.get(chartNum).isEmpty();
    }

    /**
     * 群发消息文本消息
     *
     * @param message 消息内容
     */
    public static void sendMessageToUsersByText(String ChartNum, String message) {
        Collection<Session> values = ChartNumSessionsMap.get(ChartNum);
        for (Session value : values) {
            if(value.isOpen()) {
                sendMessageToUserByText(value, message);
            }
        }
    }

    /**
     * 发送文本消息
     *
     * @param message  消息内容
     */
    public static void sendMessageToUserByText(Session session, String message) {
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOGGER.error("\n[发送消息异常]", e);
            }
        } else {
            LOGGER.info("\n[你已离线]");
        }
    }

    /**
     * 对象发送给客户端
     *
     * @param session 会话
     * @param obj     obj
     */
    public static void sendObjectToClient(Session session, Object obj) {
        if (session == null) return;

        try {
            session.getBasicRemote().sendObject(obj);
        } catch (IOException | EncodeException e) {
            LOGGER.error("向{}发送消息异常 {}", session.getId(), e.getMessage());
            LOGGER.error("向发送消息异常", e);
        }
    }

    /**
     * 向所有客户发送对象
     *
     * @param obj obj
     */
    public static void sendObjectToClients(Object obj) {
        Collection<Session> sessions = USERS.values().stream().map(ChartNumUser::getSession).collect(Collectors.toList());
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendObject(obj);
            } catch (IOException | EncodeException e) {
                LOGGER.error("向{}发送消息异常 {}", session.getId(), e.getMessage());
                LOGGER.error("向发送消息异常", e);
            }
        }
    }
}
