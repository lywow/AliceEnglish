package com.alice.collector.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.alice.aliceenglish.MyApplication;

public class SPUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SPUtil() {
    }

    private static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().
                    getSharedPreferences("collector", MyApplication.getContext().MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = sharedPreferences.edit();
        }
        return editor;
    }

    public static long getFinalUpdateTime() {
        long time = getSharedPreferences().getLong("FinalUploadTime", 0);
        long now = System.currentTimeMillis();
        Log.d("time", time + "-" + now);
        getEditor().putLong("FinalUploadTime", now);
        getEditor().commit();
        return time;
    }
}
