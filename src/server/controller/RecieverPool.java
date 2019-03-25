package server.controller;

import server.service.NoticeSender;

import java.io.DataInputStream;
import java.io.IOException;

public class RecieverPool implements Runnable {
    private DataInputStream outputStream;

    public RecieverPool(DataInputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        System.out.println("单路接收器已启动于 " + outputStream);
        String message = "";
        while (true) {
            try {
                message = outputStream.readUTF();
                new NoticeSender("logNoSpace", "!");
            } catch (IOException e) {
                //失去客户端连接，停止接收并回收内存
                new NoticeSender("logNoSpace", "?");
                break;
            }
            if (!message.isEmpty()) new NoticeSender("chat", message);
        }
    }
}
