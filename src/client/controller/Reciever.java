package client.controller;

import client.service.NoticeSender;

public class Reciever implements Runnable {
    @Override
    public void run() {
        new NoticeSender("log", "接收器已启动.");
        try {
            while (true) {
                String message = Connector.dataInputStream.readUTF();
                if (message.equals(":::!!!ServerShutdownNOW!!!:::")) {
                    GUI.status.setText("服务端已关闭! 使用\"/connect IP:端口\"重连");
                    new NoticeSender("warn", "服务端已关闭!");
                } else {
                    new NoticeSender("chat","[Server]-" + message);
                }
            }
        } catch (Exception e) {
        }
    }
}