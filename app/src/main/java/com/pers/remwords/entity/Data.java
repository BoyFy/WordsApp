package com.pers.remwords.entity;


import android.app.Application;

public class Data extends Application {
    private String uanme;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUanme() {
        return uanme;
    }

    public void setUanme(String uanme) {
        this.uanme = uanme;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
