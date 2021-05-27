package com.alice.collector.inserter;


import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alice.collector.CollectorConfig;
import com.alice.collector.data.Motion;
import com.alice.collector.data.MotionDAO;
import com.alice.collector.util.VIewPathUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.alice.collector.CollectorConfig.shineActivityTimeSpan;


/**
 * 拦截事件处理
 */
public class InterceptEventHanlder {

    private static String TAG = "tracepoint";
    private static Map<String, Long> map = new HashMap<>();

    //------------------- activity 事件接收

    public static void activityOnResume(Activity activity) {
        recordTimeForActivityORFragment(activity.getClass().getName());
    }

    public static void activityOnPause(Activity activity) {
        saveMotionForActivityOrFragment(activity.getClass().getName(),
                activity.getClass().getName(), Motion.TYPE_ACTIVITY_TIME);
    }

    //------------------- fragment 事件接收

    public static void setUserVisibleHint(Fragment fragment, boolean visiable) {
        if (visiable) {
            recordTimeForActivityORFragment(fragment.getClass().getName());
        } else {
            saveMotionForActivityOrFragment(fragment.getClass().getName(),
                    "", Motion.TYPE_FRAGMENT_TIME);
        }
    }

    public static void onHiddenChanged(Fragment fragment, boolean hidden) {
        if (!hidden) {
            recordTimeForActivityORFragment(fragment.getClass().getName());
        } else {
            saveMotionForActivityOrFragment(fragment.getClass().getName(),
                    "", Motion.TYPE_FRAGMENT_TIME);
        }
    }

    public static void setUserVisibleHint(androidx.fragment.app.Fragment fragment, boolean visiable) {
        if (visiable) {
            recordTimeForActivityORFragment(fragment.getClass().getName());
        } else {
            saveMotionForActivityOrFragment(fragment.getClass().getName(),
                    "", Motion.TYPE_FRAGMENT_TIME);
        }
    }

    public static void onHiddenChanged(androidx.fragment.app.Fragment fragment, boolean hidden) {
        if (!hidden) {
            recordTimeForActivityORFragment(fragment.getClass().getName());
        } else {
            saveMotionForActivityOrFragment(fragment.getClass().getName(),
                    "", Motion.TYPE_FRAGMENT_TIME);
        }
    }

    private static void recordTimeForActivityORFragment(String name) {
        Log.e(TAG, name);
        long now = System.currentTimeMillis();
        map.put(name, now);
    }

    private static void saveMotionForActivityOrFragment(String name, String activity, int type) {
        Log.e(TAG, name);
        long now = System.currentTimeMillis();
        if (map.containsKey(name)) {
            long time = now - map.get(name);
            if (time > CollectorConfig.shineActivityTimeSpan) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (type == 3) {
                        jsonObject.put("name", name);
                    }
                    jsonObject.put("activity", activity);
                    jsonObject.put("time", time);
                    Motion motion = new Motion();
                    String[] nameStr = name.split("\\.");
                    motion.name = nameStr[nameStr.length - 1];
                    motion.time = now;
                    motion.type = type;
                    motion.info = jsonObject.toString();
                    MotionDAO.getInstance().insert(motion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //------------------- click 事件接收

    public static void onClick(View view) {
        try {
            Activity activity = VIewPathUtil.getActivity(view);
            String path = VIewPathUtil.getViewPath(activity, view);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("activity", activity.getClass().getName());
            jsonObject.put("path", path);
            Motion motion = new Motion();
            motion.name = activity.getResources().getResourceEntryName(view.getId());
            motion.time = System.currentTimeMillis();
            motion.type = Motion.TYPE_CLICK_VIEW;
            motion.info = jsonObject.toString();
            MotionDAO.getInstance().insert(motion);
            Log.e(TAG, "viewPath:" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}