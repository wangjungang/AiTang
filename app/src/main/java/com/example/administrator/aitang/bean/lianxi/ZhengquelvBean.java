package com.example.administrator.aitang.bean.lianxi;

/**
 * Created by Administrator on 2017/10/25.
 */

public class ZhengquelvBean {
    private String type;//类型
    private double probability;//正确率

    public String getType() {
        return type;
    }

    public ZhengquelvBean(String type, double probability) {
        this.type = type;
        this.probability = probability;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "ZhengquelvBean{" +
                "type='" + type + '\'' +
                ", probability=" + probability +
                '}';
    }
}
