package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Data
@Configuration
@ConfigurationProperties(prefix = "base")
public class CommonConfig {
    private String wgToken = "";
    private String webSsh;
    private Integer webSshPort = 9992;

    public String getWebSsh() {
        if (StringUtils.isEmpty(this.webSsh))
            return "true";
        return this.webSsh;
    }
}