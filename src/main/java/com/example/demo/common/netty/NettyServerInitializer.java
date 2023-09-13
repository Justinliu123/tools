package com.example.demo.common.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 因为是基于Http协议的所以采用Http的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 是以块的方式写, 添加ChunkedWriteHandler(分块写入处理程序)
        pipeline.addLast(new ChunkedWriteHandler());
        /*
         * http 数据在传输过程中是分段的 http Object aggregator 就是可以将多个段聚合
         * 这就是为什么 当浏览器发送大量数据时, 就会出现多次http请求
         * 参数为 : 最大内容长度
         */
        pipeline.addLast(new HttpObjectAggregator(63488));
        /*
         * 对应WebSocket 他的数据时以桢(frame) 形式传递
         * 可以看到WebSocketFrame下面有6个子类
         * 浏览器请求时: ws://localhost:9998/xxx 请求的url
         * 核心功能是将http协议升级为ws协议 保持长链接
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义Handler, 处理业务逻辑
        pipeline.addLast(new NettyHandler());
    }
}