package com.example.demo.tool.sftp;

import com.example.demo.tool.pool.ParamObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 33099
 * @date 2023/09/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SftpConnectParam implements ParamObject {
    private String host;
    private Integer port;
    private String username;
    private String password;

    @Override
    public String getKey() {
        return host + "-" + username;
    }
}
