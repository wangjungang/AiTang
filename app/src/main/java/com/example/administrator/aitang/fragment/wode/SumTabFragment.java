package com.example.administrator.aitang.fragment.wode;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.zhibo.education.fragment.tab.ChatRoomTabFragment;
import com.example.administrator.aitang.zhibo.education.fragment.tab.MessageTabFragment;
import com.example.administrator.aitang.zhibo.education.fragment.tab.OnlinePeopleTabFragment;
import com.example.administrator.aitang.zhibo.im.ui.tab.reminder.ReminderId;

/**
 * Created by Administrator on 2017/12/26.
 */

public enum SumTabFragment {
    RTS(0, ReminderId.RTS, RTSTab.class, R.string.chat_room_rts, R.layout.rts_tab),
    CHAT_ROOM_MESSAGE(1, ReminderId.SESSION, MessageTabFragment.class, R.string.chat_room_message, R.layout.chat_room_message_tab),
    ONLINE_PEOPLE(2, ReminderId.CONTACT, OnlinePeopleTabFragment.class, R.string.chat_room_online_people, R.layout.chat_room_people_tab);

    public final int tabIndex;

    public final int reminderId;

    public final Class<? extends ChatRoomTabFragment> clazz;

    public final int resId;

    public final int fragmentId;

    public final int layoutId;

    SumTabFragment(int index, int reminderId, Class<? extends ChatRoomTabFragment> clazz, int resId, int layoutId) {
        this.tabIndex = index;
        this.reminderId = reminderId;
        this.clazz = clazz;
        this.resId = resId;
        this.fragmentId = index;
        this.layoutId = layoutId;
    }

    public static final SumTabFragment fromTabIndex(int tabIndex) {
        for (SumTabFragment value : SumTabFragment.values()) {
            if (value.tabIndex == tabIndex) {
                return value;
            }
        }

        return null;
    }

    public static final SumTabFragment fromReminderId(int reminderId) {
        for (SumTabFragment value : SumTabFragment.values()) {
            if (value.reminderId == reminderId) {
                return value;
            }
        }

        return null;
    }
}
