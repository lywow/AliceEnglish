package com.alice.collector.network;

import android.util.Log;

import com.alice.collector.CollectorConfig;
import com.alice.collector.data.Motion;
import com.alice.collector.data.MotionDAO;
import com.alice.collector.util.GsonUtil;
import com.alice.collector.util.SPUtil;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {
    private static OkHttpClient client = new OkHttpClient();
//    Response response=client.newCall(request).execute();

    public static void send() {
        List<Motion> list = MotionDAO.getInstance().queryFinalTime(SPUtil.getFinalUpdateTime());
        if (list != null && list.size() > 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user", CollectorConfig.user);
            jsonObject.addProperty("value", GsonUtil.getInstance().toJson(list));
            Log.e("send", jsonObject.toString());
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:1922/receive/motion")
                    .post(body)
                    .build();

            Log.e("send", "上传埋点数据" + System.currentTimeMillis());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("失败.....");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(response.body().string());
                }
            });
        } else {
            Log.e("send", "没有产生数据" + System.currentTimeMillis());
        }

    }
}
