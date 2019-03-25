package server.controller;

import com.sun.source.tree.CompoundAssignmentTree;
import server.service.NoticeSender;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class StartSending extends Thread {
    //所有通道写入流集合
    public static List<DataOutputStream> outputList = new ArrayList<DataOutputStream>();
    public static List<DataInputStream> inputList = new ArrayList<DataInputStream>();

    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;

    public void addClient(Socket socket) {
        try {
            //socket.setSoTimeout(500);
            inputList.add(new DataInputStream(socket.getInputStream()));
            new Thread(new RecieverPool(new DataInputStream(socket.getInputStream()))).start();
            outputList.add(new DataOutputStream(socket.getOutputStream()));
        } catch (Exception e) {
            new NoticeSender("warn","客户端连接失败: " + socket.getInetAddress().getHostAddress());
        }
    }

    @Override
    public void run() {
        //变量：获取特征码
        double currentCode = 0.1;
        while (true) {
            new NoticeSender("logNoSpace", ".");
            //>>获取消息和特征码并发送数据包
            //消息接口：Definer.message;
            //特征码接口：Definer.code;
            if (currentCode != Definer.code) {
                new NoticeSender("info","广播新消息：" + Definer.code + " : " + Definer.message);
                //>> 广播消息
                //拼接特征码和消息
                //String finalMessage = Definer.code + "|" + Definer.message;
                printer(Definer.message);
            }
            currentCode = Definer.code;
            try {
                Thread.sleep(Definer.times);
            } catch (Exception e) {}
        }
    }

    //广播
    public static void printer(String finalMessage) {
        List<DataOutputStream> offlines = new ArrayList<DataOutputStream>();
        for (DataOutputStream i:outputList) {
            try {
                i.writeUTF(finalMessage);
                new NoticeSender("logNoSpace", "!");
            } catch (Exception e) {
                new NoticeSender("logNoSpace", "?");
                //先记下来已经下线的客户端，秋后算账
                offlines.add(i);
            }
        }

        //秋后算账，删除下线客户端信息
        for (DataOutputStream i:offlines) {
            try {
                outputList.remove(i);
            } catch (Exception e) {}
        }
    }

    public static void killAll() throws ConnectException {
        boolean hasErr = false;
        //用特征码通知客户端服务端即将关闭
        for (DataOutputStream i:outputList) {
            try {
                i.writeUTF(":::!!!ServerShutdownNOW!!!:::");
            } catch (Exception e) {}
        }

        //断开输入输出流连接
        for (DataOutputStream i:outputList) {
            try {
                i.close();
            } catch (Exception e) {
                new NoticeSender("warn","断开客户端" + i + "输出流失败!");
                hasErr = true;
            }
        }

        for (DataInputStream i:inputList) {
            try {
                i.close();
            } catch (Exception e) {
                new NoticeSender("warn","断开客户端" + i + "输入流失败!");
                hasErr = true;
            }
        }

        if (hasErr) {
            throw new ConnectException("无法断开与客户端的连接!");
        }
    }
}
