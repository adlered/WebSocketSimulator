package client.controller;

import client.service.NoticeSender;
import client.tools.RandomNum;

import java.util.ArrayList;
import java.util.List;

//发送消息
public class Sender implements Runnable {
    private String message;
    private double code;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void run() {
        boolean simpleMessage = true;
        if (message.equals("/help")) {
            simpleMessage = false;
            List<String> list = new ArrayList();
            list.add("> ========================== <");
            list.add("> 帮助菜单:");
            list.add("> 更改或重连服务端 -- /connect IP:端口");
            list.add("> ========================== <");
            for (String i : list) {
                new NoticeSender("logNoSpace", i + "\n");
            }
        }
        if (message.indexOf("/connect") != -1) {
            try {
                simpleMessage = false;
                String[] getIPAndPort = message.split(" ");
                String[] splitIPAndPort = getIPAndPort[1].split(":");
                String ip = splitIPAndPort[0];
                int port = Integer.parseInt(splitIPAndPort[1]);
                //设置参数
                Definer.ip = ip;
                Definer.port = port;
                //开始关闭旧连接
                Client.connector.stop();
                Client.reciever.stop();
                //开启新连接
                Client.connector = new Thread(new Connector());
                Client.connector.run();
                Client.reciever = new Thread(new Reciever());
                Client.reciever.start();
            } catch (Exception e) {
                e.printStackTrace();
                simpleMessage = true;
            }
        }
        if (simpleMessage == true) {
            Definer.message = message;
            //随机生成特征码
            Definer.code = RandomNum.sumDecimal(0, 65535, false);
            //发送信息到服务端
            try {
                Connector.dataOutputStream.writeUTF(Definer.message);
                new NoticeSender("info", "已推送消息: " + Definer.code + " : " + Definer.message);
            } catch (Exception e) {
                GUI.status.setText("与服务器连接断开! 使用\"/connect IP:端口\"重连");
                new NoticeSender("warn", "服务端连接失败! 请检查地址和防火墙...");
                new NoticeSender("info", "输入\"/connect IP:端口\"可尝试重连指定服务端!");
                new NoticeSender("warn", "消息推送失败! " + Definer.code + " : " + Definer.message);
            }
        }
    }
}