package client.controller;

import client.service.NoticeSender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connector implements Runnable {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    @Override
    public void run() {
        new NoticeSender("log", "正在连接服务端" + Definer.ip + ":" + Definer.port + ".");
        GUI.status.setText("正在连接服务端 " + Definer.ip + ":" + Definer.port);
        boolean error = false;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(Definer.ip, Definer.port), 5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            GUI.status.setText("连接失败! 使用\"/connect IP:端口\"重连");
            new NoticeSender("warn", "服务端连接失败! 请检查地址和防火墙...");
            new NoticeSender("info", "输入\"/connect IP:端口\"可尝试重连指定服务端!");
        } else {
            GUI.status.setText("已连接上 " + Definer.ip + ":" + Definer.port);
            new NoticeSender("log", "成功与服务端建立连接!");
        }
    }
}
