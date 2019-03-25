package client.controller;

import client.service.NoticeSender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    //广播消息输入框
    JTextField broadcastInput = new JTextField();
    //发送按钮
    JButton sendButton = new JButton("推送消息");
    //控制台
    static JTextArea console = new JTextArea();
    //滚动条
    static JScrollPane jScrollPane = new JScrollPane(console);
    //自动滚动
    static JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    //服务器状态
    static JLabel status = new JLabel("连接中...");

    public void startGUI() {
        System.out.println("Initializing Graphic-UI...");
        //初始化窗口
        setTitle("WebSocketSimulator-Client GitHub: AdlerED");
        setSize(600, 430);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //new StopServer();
                System.exit(0);
            }
        });
        //广播消息输入框
        broadcastInput.setBounds(5, 5, 500, 50);
        add(broadcastInput);
        broadcastInput.addActionListener(new Listener());
        //发送按钮
        sendButton.setBounds(505, 5, 90, 50);
        add(sendButton);
        sendButton.addActionListener(new Listener());
        //控制台
        jScrollPane.setBounds(5, 60, 590, 310);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //禁止编辑
        console.setEditable(false);
        //激活自动换行功能
        console.setLineWrap(true);
        //激活断行不断字功能
        console.setWrapStyleWord(true);
        add(jScrollPane);
        //连接状态
        status.setBounds(5, 370, 590, 30);
        add(status);
        //询问端口
        try {
            String port = JOptionPane.showInputDialog("请输入服务器\"IP:端口\"(例:127.0.0.1:7426(留空默认)):");
            String[] res = port.split(":");
            int intPort = Integer.parseInt(res[1]);
            if (!res[0].isEmpty() && !res[1].isEmpty()) {
                if (intPort >= 1 && intPort <= 65535) {
                    Definer.port = intPort;
                }
                Definer.ip = res[0];
            }
        } catch (Exception e) {
            Definer.ip = "127.0.0.1";
            Definer.port = 7426;
        }
        //启动完毕，显示窗口
        new NoticeSender("log", "窗口已启动!");
        setVisible(true);
    }

    class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sendButton) {
                send();
            }

            if (e.getSource() == broadcastInput) {
                send();
            }
        }

        public void send() {
            //获取要发送的信息
            String message = broadcastInput.getText();
            //如果消息不为空，发送
            if (!message.isEmpty()) {
                //调用Sender
                Sender sender = new Sender();
                sender.setMessage(message);
                sender.run();
                //清空输入框内容
                broadcastInput.setText("");
            }
        }
    }

    public static void appendConsole(String message) {
        console.append(message);
        jScrollBar.setValue(jScrollBar.getMaximum());
    }
}
