package com.alice.collector.network;

import android.util.Log;

import com.alice.collector.data.Motion;
import com.alice.collector.data.MotionDAO;
import com.alice.collector.util.GsonUtil;
import com.alice.collector.util.SPUtil;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class SocketInstance {
    private static Socket socket;
    private static BufferedWriter bufferedWriter;
//    private static BufferedReader bufferedReader;

    public static boolean initSocket() {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket("10.0.2.2", 19222);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Log.e("socket", "link success");
            }
        } catch (Exception e) {
            Log.e("socket", "link failed");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int send() {
        try {
            if (initSocket()) {
                List<Motion> list = MotionDAO.getInstance().queryFinalTime(SPUtil.getFinalUpdateTime());
                if (list != null && list.size() > 0) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user", 1000);
                    jsonObject.addProperty("value", GsonUtil.getInstance().toJson(list));
                    Log.e("send", jsonObject.toString());
                    bufferedWriter.write(jsonObject.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    Log.e("send", "上传埋点数据" + System.currentTimeMillis());
                } else {
                    Log.e("send", "没有产生数据" + System.currentTimeMillis());
                }
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
