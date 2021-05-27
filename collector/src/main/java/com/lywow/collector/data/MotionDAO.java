package com.lywow.collector.data;

import android.content.Context;

public class MotionDAO {

    private static DBAdapter instance;

    public MotionDAO(Context context){
        instance=new DBAdapter(context);
        instance.open();
    }

    public static DBAdapter getInstance() {
        return instance;
    }


}
