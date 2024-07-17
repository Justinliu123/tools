package com.example.demo.common.netty;

import com.example.demo.config.CommonConfig;
import com.example.demo.util.*;

import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class ApplicationStartListener implements ApplicationRunner {

    @Autowired
    CommonConfig commonConfig;

    @Autowired
    private ServletContext servletContext;

    // 系统初始化，启动Netty 由于Netty服务启动后会阻塞线程，因此在异步线程中去启动Netty。
    public void run(ApplicationArguments args) {
        // 获取jar包路径
        ApplicationHome h = new ApplicationHome(getClass());
        String jarPath = h.getSource().getParentFile().toString();
        log.debug("jar包路径--------" + jarPath);
        StaticKeys.JAR_PATH = jarPath;

        StaticKeys.SERVER_WGTOKEN_MD5STR = MD5Utils.GetMD5Code(commonConfig.getWgToken());

        // 启动NettyServer服务 端口9998
        try {
            servletContext.setAttribute("webSsh", commonConfig.getWebSsh());
            if ("true".equals(commonConfig.getWebSsh())) {
                log.info("NettyServer服务启动，端口：" + commonConfig.getWebSshPort());
                NettyServer.start(commonConfig.getWebSshPort());
            }
        } catch (Exception e) {
            log.error("NettyServer服务启动错误：", e);
        }
    }
}
