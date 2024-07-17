package com.example.demo.common;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.websocket.GuacamoleWebSocketTunnelEndpoint;
import org.springframework.stereotype.Component;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint(value = "/ssh", subprotocols = "guacamole")
//@Component
public class SSHTunnel extends GuacamoleWebSocketTunnelEndpoint {
    private static String guacIp = "172.17.24.235";
    private static Integer guacPort = 4822;

    @Override
    protected GuacamoleTunnel createTunnel(Session session, EndpointConfig endpointConfig) throws GuacamoleException {
        // 根据传入参数从数据库中查找连接信息
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();

        configuration.setParameter("hostname", "172.17.27.57");
        configuration.setParameter("port", "22");
        configuration.setParameter("username", "root");
        configuration.setParameter("password", "root");

        configuration.setProtocol("ssh"); // 远程连接协议

        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(new InetGuacamoleSocket(guacIp, guacPort), configuration);

        GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);

        return tunnel;
    }
}