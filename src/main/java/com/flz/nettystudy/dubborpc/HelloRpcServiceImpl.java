package com.flz.nettystudy.dubborpc;

public class HelloRpcServiceImpl implements HelloRpcService {
    @Override
    public String hello(String msg) {
        System.out.println("收到来自客户端的rpc消息:" + msg);
        return "服务器已接收你的消息:" + msg;
    }
}
