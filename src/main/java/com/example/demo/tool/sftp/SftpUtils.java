package com.example.demo.tool.sftp;

import cn.hutool.extra.ssh.Sftp;
import com.example.demo.tool.pool.KeyObjectPool;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SftpUtils {
    private static final KeyObjectPool<SftpConnectParam, SftpConnectObject> ftpConnectPool = new KeyObjectPool<>(new SftpConnectFactory());

    public static List<ChannelSftp.LsEntry> getFileList(SftpConnectParam sftpConnectParam, String path) {
        Sftp sftp = getSftpConnect(sftpConnectParam);
        List<ChannelSftp.LsEntry> lsEntries = sftp.lsEntries(path);
        returnSftpConnect(sftp);
        return lsEntries;
    }

    /**
     * 获取Sftp连接，每次获取后需要归还连接
     *
     * 如果连接池中的连接被占用使用临时连接
     * @param sftpConnectParam
     * @return {@link Sftp}
     */
    private static Sftp getSftpConnect(SftpConnectParam sftpConnectParam) {
        SftpConnectObject sftpConnectObject = ftpConnectPool.borrowObject(sftpConnectParam);
        if(sftpConnectObject == null) {
            sftpConnectObject = create(sftpConnectParam);
        }
        return sftpConnectObject;
    }

    private static void returnSftpConnect(Sftp sftp) {
        if(((SftpConnectObject)sftp).getSpecial()) {
            sftp.close();
            return;
        }
        ftpConnectPool.returnObject((SftpConnectObject) sftp);
    }

    /**
     * 创建ftp连接，在连接池占用时使用
     **/
    public static SftpConnectObject create(SftpConnectParam sftpConnectParam) {
        // 特殊情况记录日志
        log.error("Sftp连接池异常，连接占用");
        SftpConnectObject ftp = null;
        try {
            ftp =  new SftpConnectObject(sftpConnectParam.getHost(), sftpConnectParam.getPort(), sftpConnectParam.getUsername(), sftpConnectParam.getPassword());
            ftp.setSpecial(true);
        } catch (Exception e) {
            log.error("创建ftp链接失败：", e);
        }
        if (ftp == null) {
            throw new RuntimeException("连接FTP服务器失败,请检查配置是否正确");
        }
        return ftp;
    }
}
