package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;

import java.io.IOException;
//import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public final class SocketUtil {

    private static String defaultHostIp = "192.168.43.201";

    private static int defaultPort = 12306;

//    private static class ClientHandler extends Handler {
//
//        public ClientHandler() {
//        }
//
//        public ClientHandler(Callback callback) {
//            super(callback);
//        }
//
//        @Override
//        public void handleMessage(Message msg) { //该方法必须重写 否则无法处理信息
//            //super.handleMessage(msg);
//            switch (msg.what) {
//
//            }
//        }
//    }

    public static InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(defaultHostIp, defaultPort);
    }

    public static Socket createClientSocket() throws IOException {
        return new Socket(defaultHostIp, defaultPort);
    }

    public static Socket createClientSocket(int connectTimeOut, int readTimeOut)
            throws IOException {
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(defaultHostIp, defaultPort), connectTimeOut); //设置 连接超时时间
        clientSocket.setSoTimeout(readTimeOut); //设置 读取超时时间
        return clientSocket;
    }

//    public static Handler createClientHandler() {
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
//        return handler;
//    }

//    public static ClientHandler createClientHandler() {
//        return new ClientHandler();
//    }

    public static Handler createHandler(Handler.Callback callback) {
        return new Handler(callback);
    }

    public static Thread createThread(Runnable runnable) {
        return new Thread(runnable);
    }

    public static class DemoClientRunnable implements Runnable {

        private Socket socket;

        private boolean readFlag;

        private boolean writeFlag;

        public interface DealMethods {
            public Socket socket = new Socket();

            public boolean connect() throws IOException;

            public boolean receive() throws IOException;

            public boolean send() throws IOException;

            public boolean close() throws IOException;
        }

        private DealMethods operation = null;

        public DemoClientRunnable(Socket socket) {
            this.socket = socket;
            readFlag = writeFlag = true;
        }

        public DemoClientRunnable(DealMethods operation) {
            this.operation = operation;
        }

        public DemoClientRunnable(Socket socket, boolean readFlag, boolean writeFlag) {
            this.socket = socket;
            this.readFlag = readFlag;
            this.writeFlag = writeFlag;
        }

        @Override
        public void run() {
            if (operation != null) {
                interfaceMode();
            } else {
                normalMode();
            }
        }

        private void interfaceMode() {
            boolean connect = false;
            boolean receive = false;
            boolean send = false;
            boolean close = false;
           try {
               connect = operation.connect();
               receive = operation.receive();
               send = operation.send();
               close = operation.close();
           } catch (IOException e) {
               e.printStackTrace();
               if (!connect) {
                   LogUtil.e(Utility.WATCH_TAG, "网络连接失败!请检查网络!");
               }
               if (!receive) {
                   LogUtil.e(Utility.WATCH_TAG, "获取服务器数据出错!");
               }
               if (!send) {
                   LogUtil.e(Utility.WATCH_TAG, "发送本地数据出错!");
               }
               if (!close) {
                   LogUtil.e(Utility.WATCH_TAG, "关闭服务器流出错!");
               }
           }
        }

        private void normalMode() {

        }


    }

}
