package com.example.administrator.tianrui6;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2018\5\14 0014.
 */

public class WifiSocket {
    Socket socket = null;
    String buffer = "";

    String data;

    public WifiSocket(Context context){

    }

    public String getSocketData(){
        return data;
    }
    public void setSocketdata(String data){
        this.data = data;
    }

    public void Wifi_Client(final String data){
        new Thread(){
            //建一个线程防止阻塞UI线程ip
            public void run(){
                super.run();
                try {
                    Socket socket = new Socket("192.168.2.127",8080);

                    //建立连接,因为genymotion的模拟器的本地ip不同于一般的模拟器，所以ip地址要用这个
                    sleep(1000);
                    // 获取服务器返回的数据
                    //2、获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    PrintWriter pw =new PrintWriter(os);//将输出流包装成打印流
                    pw.write(data);
                    pw.flush();

                    // 从Socket当中得到InputStream对象
                    InputStream inputStream = socket.getInputStream();
                    byte buffer[] = new byte[1024 * 4];
                    int temp = 0;
                    // 从InputStream当中读取客户端所发送的数据
                    while ((temp = inputStream.read(buffer)) != -1) {
                        setSocketdata(new String(buffer,0,temp));
                        //System.out.println(new String(buffer, 0, temp));

                    }

                    socket.shutdownOutput();
                    pw.close();
                    os.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }







    public void ServerReceviedByTcp() {
        // 声明一个ServerSocket对象
        ServerSocket serverSocket = null;
        try {
            // 创建一个ServerSocket对象，并让这个Socket在1989端口监听
            serverSocket = new ServerSocket(8881);
            // 调用ServerSocket的accept()方法，接受客户端所发送的请求，
            // 如果客户端没有发送数据，那么该线程就停滞不继续
            Socket socket = serverSocket.accept();
            // 从Socket当中得到InputStream对象
            InputStream inputStream = socket.getInputStream();
            byte buffer[] = new byte[1024 * 4];
            int temp = 0;
            // 从InputStream当中读取客户端所发送的数据
            while ((temp = inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, temp));
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void Wifi_Ser(){

        new Thread(){
            //建一个线程防止阻塞UI线程
            byte[] k = new byte[50];
            public void run(){
                super.run();
                try {
                    Socket socket = new Socket("192.168.2.127",8881);
                    //建立连接,因为genymotion的模拟器的本地ip不同于一般的模拟器，所以ip地址要用这个
                    sleep(1000);
                    // 获取服务器返回的数据
                    //2、获取输入流，向服务器端发送信息
                    InputStream in = socket.getInputStream();
                    in.read(k);
                    String Read = new String(k);
                    Log.e("wifi_Serrrr", String.valueOf(Read));
                    in.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }


    class MyThread extends Thread {

        public String txt1;

        public MyThread(String str) {
            txt1 = str;
        }

        @Override
        public void run() {
            //定义消息

            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                //连接服务器 并设置连接超时为5秒
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.2.127", 8080), 8000);
                //获取输入输出流
                OutputStream ou = socket.getOutputStream();
                BufferedReader bff = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                //读取发来服务器信息
                String line = null;
                buffer="";
                while ((line = bff.readLine()) != null) {
                    buffer = line + buffer;
                }
                Log.e("TAG",buffer);

                //向服务器发送信息
                ou.write("android 客户端".getBytes("gbk"));
                ou.flush();
                bundle.putString("msg", buffer.toString());
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                //  myHandler.sendMessage(msg);
                //关闭各种输入输出流
                bff.close();
                ou.close();
                socket.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                //  myHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
