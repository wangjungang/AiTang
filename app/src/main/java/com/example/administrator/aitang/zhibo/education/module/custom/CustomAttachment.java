package com.example.administrator.aitang.zhibo.education.module.custom;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

import org.json.JSONObject;


/**
 * Created by zhoujianghua on 2015/4/9.
 */
public abstract class CustomAttachment implements MsgAttachment {

    protected int type;

    CustomAttachment(int type) {
        this.type = type;
    }

    public void fromJson(JSONObject data) {
        if (data != null) {
            parseData(data);
        }
    }

//    @Override
//    public String toJson(boolean send) {
//        return CustomAttachParser.packData(type, packData());
//    }


    @Override
    public String toJson(boolean send) {
        return CustomAttachParser.packData(type,packData());
    }

    public int getType() {
        return type;
    }

    protected abstract void parseData(JSONObject data);
    protected abstract JSONObject packData();
}
