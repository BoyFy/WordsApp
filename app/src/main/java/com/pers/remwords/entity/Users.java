package com.pers.remwords.entity;

public class Users {
    private String uname;
    private String psd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public Users(String uname, String psd) {
        this.uname = uname;
        this.psd = psd;
    }
}
