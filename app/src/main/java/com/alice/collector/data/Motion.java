package com.alice.collector.data;

public class Motion {
    public String id;
    public long time;
    public int type;
    public String name;
    public String info;//json字串

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        if (id == null || id.isEmpty()) {
            id = type + name + time;
        }
        return id;
    }

    /**
     * 1，view的id(name)，view的路径
     * 2，activity的name，activity显示的时长
     * 3，fragment的name，fragment显示的时长
     */
    public static final int TYPE_CLICK_VIEW = 1;
    public static final int TYPE_ACTIVITY_TIME = 2;
    public static final int TYPE_FRAGMENT_TIME = 3;
}
