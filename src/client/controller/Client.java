package client.controller;

import client.service.NoticeSender;

/**
 * 模拟WebSocket-Client
 */

public class Client {
    //版本号
    public static String version = "1.0.0";
    public static Thread connector;
    public static Thread reciever;

    public static void main(String[] args) {
        System.out.println("WebSocket-Client now starting. Please wait...");
        new NoticeSender("info", "欢迎使用WebSocketSimulator-Client V" + version + " !");
        new NoticeSender("info", "输入 /help 可查看帮助...");
        //启动图形界面
        new GUI().startGUI();
        //连接服务器，使用run阻塞线程，否则接收器比连接先建立!
        connector = new Thread(new Connector());
        connector.run();
        //启动接收器
        reciever = new Thread(new Reciever());
        reciever.start();
    }
}