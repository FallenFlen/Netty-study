package com.flz.nettystudy.chatroom;

import com.flz.nettystudy.chatroom.core.server.MyNettyChatRoomServer;

public class ChatRoomApp {
    public static void main(String[] args) {
        new MyNettyChatRoomServer(8079).start();
    }
}
