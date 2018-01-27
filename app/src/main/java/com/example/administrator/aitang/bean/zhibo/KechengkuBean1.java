package com.example.administrator.aitang.bean.zhibo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class KechengkuBean1 implements Parcelable {
    private List<String> timeArray;
    public static KechengkuBean.DataBean bean;

    public KechengkuBean1(List<String> timeArray, KechengkuBean.DataBean bean) {
        this.timeArray = timeArray;
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "KechengkuBean1{" +
                "timeArray=" + timeArray +
                ", bean=" + bean +
                '}';
    }

    public List<String> getTimeArray() {
        return timeArray;
    }

    public void setTimeArray(List<String> timeArray) {
        this.timeArray = timeArray;
    }

    public KechengkuBean.DataBean getBean() {
        return bean;
    }

    public void setBean(KechengkuBean.DataBean bean) {
        this.bean = bean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.timeArray);
        dest.writeParcelable(this.bean, flags);
    }

    protected KechengkuBean1(Parcel in) {
        this.timeArray = in.createStringArrayList();
        this.bean = in.readParcelable(KechengkuBean.DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<KechengkuBean1> CREATOR = new Parcelable.Creator<KechengkuBean1>() {
        @Override
        public KechengkuBean1 createFromParcel(Parcel source) {
            return new KechengkuBean1(source);
        }

        @Override
        public KechengkuBean1[] newArray(int size) {
            return new KechengkuBean1[size];
        }
    };
}
