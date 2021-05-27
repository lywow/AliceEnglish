package com.alice.aliceenglish;

import android.app.Application;
import android.content.Context;

import com.alice.aliceenglish.util.WordDAO;
import com.alice.collector.CollectorConfig;
import com.xuexiang.xui.XUI;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        context = this;
        CollectorConfig.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
