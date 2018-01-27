package com.example.administrator.aitang.bean.lianxi;

/**
 * Created by Administrator on 2017/10/25.
 */

public class DatishuBean {
    private String type;
    private double timunum;

    public DatishuBean(String type, double timunum) {
        this.type = type;
        this.timunum = timunum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DatishuBean{" +
                "type='" + type + '\'' +
                ", timunum=" + timunum +
                '}';
    }

    public double getTimunum() {
        return timunum;
    }

    public void setTimunum(double timunum) {
        this.timunum = timunum;
    }
}
