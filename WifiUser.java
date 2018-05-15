package com.example.administrator.tianrui6;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\5\12 0012.
 */

public class WifiUser implements Serializable {
    private String ssid;
    private int level;

    public String getName() {
        return ssid;
    }
    public void setName(String name) {
        this.ssid = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public WifiUser(String name,int level) {
        this.ssid=name;
        this.level=level;
    }
}
