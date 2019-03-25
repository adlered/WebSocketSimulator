package client.service;

import client.controller.GUI;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送消息类
 */
public class NoticeSender {
    //记录上一条信息，用于检测上条信息是否是".!?"以判断是否换行输出
    static String lastestMessage = null;
    //第几条信息
    static int times = 0;

    public NoticeSender(String level, String message) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat chatSimpleDateFormat = new SimpleDateFormat("HH:mm");
        switch (level) {
            case "log":
                append("[LOG]-[Client]-[" + simpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "info":
                append("[INFO]-[Client]-[" + simpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "warn":
                append("[WARN]-[Client]-[" + simpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "chat":
                append("[CHAT]-[" + chatSimpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "logNoSpace":
                append(message, false);
                break;
        }
    }

    public void append(String message, boolean autoAddEnter) {
        //计数器们
        lastestMessage = message;
        times++;
        //输出
        if (autoAddEnter) {
            GUI.appendConsole(message + "\n");
            System.out.print(message + "\n");
        } else {
            GUI.appendConsole(message);
            System.out.print(message);
        }
    }
}
