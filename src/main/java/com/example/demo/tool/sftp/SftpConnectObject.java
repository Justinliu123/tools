package com.example.demo.tool.sftp;

import cn.hutool.extra.ssh.Sftp;
import com.example.demo.tool.pool.KeyObject;

public class SftpConnectObject extends Sftp implements KeyObject {
    private Boolean special = false;
    public SftpConnectObject(String sshHost, int sshPort, String sshUser, String sshPass) {
        super(sshHost, sshPort, sshUser, sshPass);
    }

    @Override
    public String getKey() {
        return ftpConfig.getHost() + "-" + ftpConfig.getUser();
    }

    @Override
    public void close() {
        super.close();
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }
}
