package server.controller;

import server.service.NoticeSender;

public class StopServer {
    public StopServer() {
        try {
            StartSending.killAll();
            new NoticeSender("info","已断开与客户端的连接! 正在关闭服务端...");
            //等待结束
            Thread.sleep(1000);
            new NoticeSender("info","服务端已关闭!");
        } catch (Exception e) {
            new NoticeSender("warn","无法断开与客户端的连接 (未连接或超时) ! 强制关闭服务端中...");
            //等待结束
            try {
                Thread.sleep(2000);
            } catch (Exception f) {}
            new NoticeSender("info","服务端已关闭!");
        }
    }
}