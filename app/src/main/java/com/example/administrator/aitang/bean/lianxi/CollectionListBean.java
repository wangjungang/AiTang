package com.example.administrator.aitang.bean.lianxi;

import java.io.Serializable;
import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/12/9
 * Describe : 收藏列表数据类
 */

public class CollectionListBean implements Serializable {


    /**
     * code : 200
     * data : [{"name":"常识分类","uqid":"6,8,9,"},{"name":"判断推理分类","uqid":"10,"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * name : 常识分类
         * uqid : 6,8,9,
         */

        private String name;
        private String uqid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUqid() {
            return uqid;
        }

        public void setUqid(String uqid) {
            this.uqid = uqid;
        }
    }
}
