package com.alice.aliceenglish.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alice.aliceenglish.entity.Word;
import com.alice.aliceenglish.entity.WordJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Translate implements Runnable{
    private WordJson wordJson;
    private String english;

    public Word getWord() {
        if(wordJson==null){
            return null;
        }
        return wordJson.toWord();
    }

    public Translate(String english){
        this.english=english;
    }

    @Override
    public void run() {
        try {
            wordJson=getTranslation(english);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";
    private static final String APP_KEY = "0a6b2db4a73a4aab";
    private static final String APP_SECRET = "7FEUulN48N0E3OtqMcmsHkl1mE2PuJLR";
    private static final Gson gson=new GsonBuilder().create();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private WordJson getTranslation(String str) throws IOException, ParseException {
        System.out.println("查询单词："+str);
        Map<String,String> params = new HashMap<String,String>();
        String q = str;
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "en");
        params.put("to", "zh-CHS");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        /** 处理结果 */
        String json=Http.requestForHttp(YOUDAO_URL,params);
        System.out.println("接受请求"+json);
        return gson.fromJson(json, WordJson.class);
    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    /**
     * 生成加密字段
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
