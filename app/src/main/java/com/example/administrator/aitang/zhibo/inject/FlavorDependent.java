package com.example.administrator.aitang.zhibo.inject;

import android.app.Activity;

import com.example.administrator.aitang.zhibo.education.activity.IdentifyActivity;
import com.example.administrator.aitang.zhibo.education.helper.ChatRoomHelper;
import com.example.administrator.aitang.zhibo.education.module.custom.CustomAttachParser;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;


/**
 * Created by huangjun on 2016/3/15.
 */
public class FlavorDependent implements IFlavorDependent{

    @Override
    public String getFlavorName() {
        return "education";
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return IdentifyActivity.class;
    }

    @Override
    public MsgAttachmentParser getMsgAttachmentParser() {
        return new CustomAttachParser();
    }

    @Override
    public void onLogout() {
        ChatRoomHelper.logout();
    }

    public static FlavorDependent getInstance() {
        return InstanceHolder.instance;
    }

    public static class InstanceHolder {
        public final static FlavorDependent instance = new FlavorDependent();
    }

    @Override
    public void onApplicationCreate() {
        ChatRoomHelper.init();
    }
}
