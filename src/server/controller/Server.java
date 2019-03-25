package server.controller;

import server.service.NoticeSender;

/**
 * 模拟WebSocket-Server
 * 使用消息特征码让客户端接收消息
 */

//主方法
public class Server {
    //版本号
    public static String version = "1.0.0";
    public static void main(String[] args) {
        System.out.println("WebSocket-Server now starting. Please wait...");
        new NoticeSender("info","欢迎使用WebSocketSimulator-Server V" + version + " !");
        //启动图形界面
        new GUI().startGUI();
        //启动网络线程
        Networker networker = new Networker();
        Definer.executorService.execute(networker);
    }
}