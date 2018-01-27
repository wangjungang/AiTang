package com.example.administrator.aitang.zhibo.education.viewholder;

import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.zhibo.education.helper.ChatRoomNotificationHelper;
import com.example.administrator.aitang.zhibo.im.session.viewholder.MsgViewHolderBase;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;


public class ChatRoomMsgViewHolderNotification extends MsgViewHolderBase {

    protected TextView notificationTextView;

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(R.id.message_item_notification_label);
    }

    @Override
    protected void bindContentView() {
        notificationTextView.setText(ChatRoomNotificationHelper.getNotificationText((ChatRoomNotificationAttachment) message.getAttachment()));
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }
}

