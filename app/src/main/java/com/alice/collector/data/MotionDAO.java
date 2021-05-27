package com.alice.collector.data;

import android.content.Context;

import com.alice.aliceenglish.MyApplication;
import com.alice.collector.CollectorConfig;

public class MotionDAO {

    private static DBAdapter instance;

    private MotionDAO(Context context) { }

    public static DBAdapter getInstance() {
        if (instance == null) {
            instance = new DBAdapter(CollectorConfig.context);
            instance.open();
        }
        return instance;
    }


}
