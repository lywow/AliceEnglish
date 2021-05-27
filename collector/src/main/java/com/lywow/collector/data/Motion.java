package com.lywow.collector.data;

public class Motion {
    public long time;
    public String activity;
    public String path;
    public String view;

    public String getID() {
        return view + time;
    }
}
