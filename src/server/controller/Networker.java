package server.controller;

import server.service.NoticeSender;

import java.net.ServerSocket;
import java.net.Socket;

//网络主线程
public class Networker implements Runnable {
    @Override
    public void run() {
        //初始化Socket
        try {
            ServerSocket serverSocket = new ServerSocket(Definer.port);
            new NoticeSender("info","服务器" + serverSocket.getLocalSocketAddress() + "已启动!");
            StartSending startSending = new StartSending();
            startSending.start();
            new NoticeSender("info","监听器已启动!");
            while (true) {
                //监听客户端连接
                Socket socket = serverSocket.accept();
                //启动发送线程
                startSending.addClient(socket);
                new NoticeSender("logNoSpace", "!");
                new NoticeSender("log","客户端" + socket.getInetAddress().getHostAddress() + "已连接.");
            }
        } catch (Exception e) {
            new NoticeSender("warn","失败! 端口被占用或无法网络访问权限.");
            System.exit(0);
        }
    }
}