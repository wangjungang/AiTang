package com.example.administrator.aitang.zhibo.im.session.viewholder;


import com.example.administrator.aitang.R;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderText extends MsgViewHolderBase {

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_text;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {

    }

    protected String getDisplayText() {
        return message.getContent();
    }
}
