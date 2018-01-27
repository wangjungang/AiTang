package com.example.administrator.aitang.bean.lianxi;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/28
 * Describe : 答题中的正确答案Bean,用来比对
 */

public class ZhengQueDaAnBean {
    int id;//套题中的序列

    String tId = "";//题号

    String type = "";//题目类型-0为选择题 1为主观题

    String isSingle = "";//单选还是多选- 0为单选 1为多选

    List<Integer> answerList = new ArrayList<>();//选择题的选项

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(String isSingle) {
        this.isSingle = isSingle;
    }

    public List<Integer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Integer> answerList) {
        this.answerList = answerList;
    }
}
