package org.sample.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liudong17
 * @date 2019-05-29 15:16
 */
public class ServerBootstrapDemo {
    public static void main(String[] args) throws UnknownHostException {
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(new DefaultEventLoop())
                .channelFactory(new ReflectiveChannelFactory(EmbeddedChannel.class))
                .childHandler(new ChannelDuplexHandler());
        ChannelFuture bind = serverBootstrap.bind(InetAddress.getLocalHost(), 2181);
        EmbeddedChannel channel = (EmbeddedChannel) bind.channel();
        System.out.println(channel.isActive());
        DefaultEventExecutorGroup group = new DefaultEventExecutorGroup(10);
    }
}
