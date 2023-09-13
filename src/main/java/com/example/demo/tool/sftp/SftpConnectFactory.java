package com.example.demo.tool.sftp;


import com.example.demo.tool.pool.KeyObjectFactory;

public class SftpConnectFactory implements KeyObjectFactory<SftpConnectParam, SftpConnectObject> {
    @Override
    public SftpConnectObject create(SftpConnectParam paramObject) {
        return new SftpConnectObject(paramObject.getHost(), paramObject.getPort(), paramObject.getUsername(), paramObject.getPassword());
    }
}
