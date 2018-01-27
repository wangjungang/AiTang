package com.example.administrator.aitang.bean.zhibo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class ClassBean1 {
    private List<String> timeArray;
    private ClassBean.DataBean dataBean;

    public ClassBean1(List<String> timeArray, ClassBean.DataBean dataBean) {
        this.timeArray = timeArray;
        this.dataBean = dataBean;
    }

    public List<String> getTimeArray() {
        return timeArray;
    }

    public void setTimeArray(List<String> timeArray) {
        this.timeArray = timeArray;
    }

    public ClassBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(ClassBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    @Override
    public String toString() {
        return "ClassBean1{" +
                "timeArray=" + timeArray +
                ", dataBean=" + dataBean +
                '}';
    }
}
