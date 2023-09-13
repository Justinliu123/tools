package com.example.demo.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void start(int port) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup1 = new NioEventLoopGroup();
        NioEventLoopGroup nioEventLoopGroup2 = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(nioEventLoopGroup1, nioEventLoopGroup2)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup1.shutdownGracefully();
            nioEventLoopGroup2.shutdownGracefully();
        }
    }
}
