package com.alice.collector.network;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.alice.collector.CollectorConfig;

public class Connect {
    private static HandlerThread handlerThread;


    public static void init() {
        SocketInstance.initSocket();
        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();

        Handler workHandler = new NetworkHandler();


        Message msg = Message.obtain();
        msg.what = 0;
        workHandler.sendMessageDelayed(msg, CollectorConfig.uploadTimeSpan);
        //mHandlerThread.quit();
    }

    private static class NetworkHandler extends Handler {

        public NetworkHandler() {
            super(handlerThread.getLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Network.send();
                Message next = Message.obtain();
                next.what = 0;
                sendMessageDelayed(next, CollectorConfig.uploadTimeSpan);
            }
        }
    }

}
