package com.alice.aliceenglish;

import android.app.Application;

import com.alice.aliceenglish.util.WordDAO;
import com.xuexiang.xui.XUI;

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        new WordDAO(this);
    }
}
