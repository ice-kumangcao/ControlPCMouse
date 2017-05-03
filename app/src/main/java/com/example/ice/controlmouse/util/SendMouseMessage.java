package com.example.ice.controlmouse.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMouseMessage implements Runnable{

	public SendMouseMessage(String message,String ip){
        operateStr=message;
        this.ip=ip;
	}
	private Socket socket;
    private int OPERATE_PORT = 9999;
    private String operateStr;
    private String ip;
	@Override
	public void run() {
        try {
            System.out.println("开始发送*****"+operateStr);
            socket = new Socket(ip,OPERATE_PORT);
            System.out.println("send message:"+operateStr);
            DataOutputStream output =new DataOutputStream(socket.getOutputStream());

            output.write(operateStr.getBytes());
            output.flush();   //刷行输出流，并且使所有缓冲的输出字节写出
            output.close();   //关闭输出流且释放资源
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("已断开连接");
        }
    }

}
