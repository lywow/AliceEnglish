package com.alice.aliceenglish.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Word implements Serializable {
    private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    private String english;
    private String chinese;
    private String phonetic;
    private int frequency;
    private String time;

    public Word(){}

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = sdf.format(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}
