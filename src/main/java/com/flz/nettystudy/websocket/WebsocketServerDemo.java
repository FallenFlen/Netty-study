package com.flz.nettystudy.websocket;

public class WebsocketServerDemo {
    public static void main(String[] args) {
        WebsocketServer websocketServer = new WebsocketServer(9324, "/");
        websocketServer.start();
    }
}
