package com.example.administrator.aitang.bean.zhibo;

/**
 * Created by Administrator on 2017/12/20.
 * 服务器录制返回的数据格式
 */

public class DataRecordingBean {
    private int bc;//包长 4字节(包含包长和时间戳)
    private int sjc;//时间戳 4字节
    private String data;//数据

    @Override
    public String toString() {
        return "DataRecordingBean{" +
                "bc='" + bc + '\'' +
                ", sjc='" + sjc + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public DataRecordingBean(int bc, int sjc, String data) {
        this.bc = bc;
        this.sjc = sjc;
        this.data = data;
    }

    public int getBc() {
        return bc;
    }

    public void setBc(int bc) {
        this.bc = bc;
    }

    public int getSjc() {
        return sjc;
    }

    public void setSjc(int sjc) {
        this.sjc = sjc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
