package com.example.administrator.aitang.bean.lianxi;

import java.util.List;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class HistoryRealQuestiionBean {
    private String code;
    private List<Data> data;

    public HistoryRealQuestiionBean(String code, List<Data> data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data{
        public String qcid;
        public String qcname;
        public String qctype;
        public String qdegree;
        public String qcscore;
        public String qcintro;
        public String users;
        public String ctime;
        public String ismake;

        public Data(String qcid, String qcname, String qctype, String qdegree, String qcscore, String qcintro, String users, String ctime, String ismake) {
            this.qcid = qcid;
            this.qcname = qcname;
            this.qctype = qctype;
            this.qdegree = qdegree;
            this.qcscore = qcscore;
            this.qcintro = qcintro;
            this.users = users;
            this.ctime = ctime;
            this.ismake = ismake;
        }

        public String getQcid() {
            return qcid;
        }

        public String getQcname() {
            return qcname;
        }

        public String getQctype() {
            return qctype;
        }

        public String getQdegree() {
            return qdegree;
        }

        public String getQcscore() {
            return qcscore;
        }

        public String getQcintro() {
            return qcintro;
        }

        public String getUsers() {
            return users;
        }

        public String getCtime() {
            return ctime;
        }

        public String getIsmake() {
            return ismake;
        }
    }

}
