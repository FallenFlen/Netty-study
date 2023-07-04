package com.flz.nettystudy.chatroom;

import com.flz.nettystudy.chatroom.core.client.MyNettyChatRoomClient;

public class ChatRoomClientApp {
    public static void main(String[] args) throws InterruptedException {
        MyNettyChatRoomClient client = new MyNettyChatRoomClient("127.0.0.1", 8079);
        client.connect();
    }
}
