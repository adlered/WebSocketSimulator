package server.controller;

import server.service.NoticeSender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    //广播消息输入框
    JTextField broadcastInput = new JTextField();
    //发送按钮
    JButton sendButton = new JButton("发送广播");
    //>>控制台
    //1.输入框
    static JTextArea console = new JTextArea();
    //2.滚动条
    static JScrollPane jScrollPane = new JScrollPane(console);
    //3.自动滚动
    static JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    //>>网络发包
    //1.输入框
    static JTextArea networkPackager = new JTextArea();
    //2.滚动条
    static JScrollPane networkPackagerPane = new JScrollPane(networkPackager);
    //3.自动滚动
    static JScrollBar networkPackagerBar = networkPackagerPane.getVerticalScrollBar();
    //网络使用情况标签
    JLabel networkUsingLable = new JLabel("网络使用情况 ( [ . ] 等待命令 [ ! ] 发包成功 [ ? ] 找不到网络路径 )");

    public void startGUI() {
        System.out.println("Initializing Graphic-UI...");
        //初始化窗口
        setTitle("WebSocketSimulator-Server GitHub: AdlerED");
        setSize(600,440);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new StopServer();
                System.exit(0);
            }
        });
        //广播消息输入框
        broadcastInput.setBounds(5,5,500,50);
        add(broadcastInput);
        broadcastInput.addActionListener(new Listener());
        //发送按钮
        sendButton.setBounds(505,5,90,50);
        add(sendButton);
        sendButton.addActionListener(new Listener());
        //>>控制台
        jScrollPane.setBounds(5,60,590,290);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //禁止编辑
        console.setEditable(false);
        //激活自动换行功能
        console.setLineWrap(true);
        //激活断行不断字功能
        console.setWrapStyleWord(true);
        add(jScrollPane);
        //>>网络发包状态
        networkPackagerPane.setBounds(5,380,590,25);
        //networkPackagerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //禁止编辑
        networkPackager.setEditable(false);
        //激活自动换行功能
        networkPackager.setLineWrap(true);
        //激活断行不断字功能
        networkPackager.setWrapStyleWord(true);
        add(networkPackagerPane);
        //>>网络使用情况标签
        networkUsingLable.setBounds(5,360,590,15);
        add(networkUsingLable);
        //询问端口
        try {
            String port = JOptionPane.showInputDialog("请输入服务器端口(留空则7426):");
            int intPort = Integer.parseInt(port);
            if (!port.isEmpty()) {
                if (intPort >= 1 && intPort <= 65535) {
                    Definer.port = intPort;
                }
            }
        } catch (Exception e) {}
        //启动完毕，显示窗口
        new NoticeSender("log","窗口已启动!");
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

    public static void appendNetworkPackager(String message) {
        if(networkPackager.getText().length() >= 145) networkPackager.setText("");
        networkPackager.append(message);
        networkPackagerBar.setValue(networkPackagerBar.getMaximum());
    }
}
