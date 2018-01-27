package com.example.administrator.aitang.bean;

import java.net.PortUnreachableException;
import java.util.List;

/**
 * Created by 武培培 on 2017/10/22.
 */

public class ExameTypeBean {
public String code;
    public List<Data> data;

    public ExameTypeBean(String code, List<Data> data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
public static class Data{
    public String testid;
    public String testpid;
    public String testname;
    public String testpath;
    public String testflag;
    public String time;
    public String testlist;

    public Data(String testid, String testpid, String testname, String testpath, String testflag, String time) {
        this.testid = testid;
        this.testpid = testpid;
        this.testname = testname;
        this.testpath = testpath;
        this.testflag = testflag;
        this.time = time;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getTestpid() {
        return testpid;
    }

    public void setTestpid(String testpid) {
        this.testpid = testpid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getTestpath() {
        return testpath;
    }

    public void setTestpath(String testpath) {
        this.testpath = testpath;
    }

    public String getTestflag() {
        return testflag;
    }

    public void setTestflag(String testflag) {
        this.testflag = testflag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTestlist() {
        return testlist;
    }

    public void setTestlist(String testlist) {
        this.testlist = testlist;
    }
}

}
