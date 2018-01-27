package com.example.administrator.aitang.bean.lianxi;

import java.util.List;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class CuotiBenBean {
    public String code;
    public List<Data> data;

    public CuotiBenBean(String code, List<Data> data) {
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
        public String qtid;
        public String qtname;
        public List<Child> child;

        public Data(String qtid, String qtname, List<Child> child) {
            this.qtid = qtid;
            this.qtname = qtname;
            this.child = child;
        }

        public String getQtid() {
            return qtid;
        }

        public String getQtname() {
            return qtname;
        }

        public List<Child> getChild() {
            return child;
        }

        public static class  Child{
            public String qtid;
            public String qtname;
            public List<Child2> child;

            public Child(String qtid, String qtname, List<Child2> child) {
                this.qtid = qtid;
                this.qtname = qtname;
                this.child = child;
            }

            public String getQtid() {
                return qtid;
            }

            public String getQtname() {
                return qtname;
            }

            public List<Child2> getChild() {
                return child;
            }

            public static  class  Child2{
                public String qtid;
                public String qtname;
                public List<Child2> child;

                public Child2(String qtid, String qtname, List<Child2> child) {
                    this.qtid = qtid;
                    this.qtname = qtname;
                    this.child = child;
                }

                public String getQtid() {
                    return qtid;
                }

                public String getQtname() {
                    return qtname;
                }

                public List<Child2> getChild() {
                    return child;
                }
            }
        }
    }
}
