package server.controller;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//变量定义类
public class Definer {
    //端口号
    public static int port = 7426;
    //线程池
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);
    //发送数据包频率
    public static int times = 50;
    //>>消息和消息特征码，防止重复接收
    //1. 消息
    public static String message = "WebSocket模拟器服务端初始化完毕! 我的GitHub：AdlerED";
    //2.特征码
    public static double code;
    //接收验证池
    public static List<DataInputStream> recieverPool = new ArrayList<DataInputStream>();
}
