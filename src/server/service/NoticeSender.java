package server.service;

import server.controller.GUI;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送消息类
 */
public class NoticeSender {
    //第几条信息
    static int times = 0;

    public NoticeSender(String level, String message) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat chatSimpleDateFormat = new SimpleDateFormat("HH:mm");
        switch (level){
            case "log":
                append("[LOG]-[Server]-[" + simpleDateFormat.format(date) + "]-" + message,true);
                break;
            case "info":
                append("[INFO]-[Server]-[" + simpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "warn":
                append("[WARN]-[Server]-[" + simpleDateFormat.format(date) + "]-" + message, true);
                break;
            case "chat":
                append("[CHAT]-[" + chatSimpleDateFormat.format(date) + "]-[Client]-" + message, true);
                break;
            case "logNoSpace":
                //append(message,false);
                GUI.appendNetworkPackager(message);
                //System.out.print(message);
                break;
        }
    }

    public void append(String message, boolean autoAddEnter) {
        //计数器们
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
