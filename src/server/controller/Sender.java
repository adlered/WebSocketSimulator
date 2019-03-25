package server.controller;

import server.service.NoticeSender;
import server.tools.RandomNum;

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
        //>>将特征码和消息提交到Definer，
        //随机生成特征码
        Definer.code = RandomNum.sumDecimal(0,65535,false);
        //提交消息到Definer消息池，等待StartSending扫描并发送
        Definer.message = message;
        new NoticeSender("log","已推送消息: " + Definer.code + " : " + Definer.message);
    }
}