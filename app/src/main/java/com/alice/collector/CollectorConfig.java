package com.alice.collector;

import android.content.Context;
import android.os.SystemClock;

import com.alice.collector.network.Connect;

public class CollectorConfig {
    public final static int UP_LOAD_TYPE_TIME = 0;
    public final static int UP_LOAD_TYPE_COUNT = 1;

    public static int uploadType = 0;
    public static int uploadCountSpan = 20;//1采用计数上报
    public static long uploadTimeSpan = 30 * 1000;//0采用定时上报
    public static long shineActivityTimeSpan = 300;
    public static int user = 1000;

    public static Context context;

    public static void init(Context c) {
        user = (int) (System.currentTimeMillis() % 100) + 1000;
        context = c;
        Connect.init();
    }

    public static void init(Context c, int spanType, int span) {
        user = (int) (System.currentTimeMillis() % 100) + 1000;
        context = c;
        Connect.init();
        if (spanType == UP_LOAD_TYPE_COUNT) {
            uploadType = UP_LOAD_TYPE_COUNT;
            uploadCountSpan = span;
        }
    }
}
