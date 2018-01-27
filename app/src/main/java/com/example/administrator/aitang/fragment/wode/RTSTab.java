package com.example.administrator.aitang.fragment.wode;

import android.os.Bundle;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.zhibo.education.fragment.ChatRoomRTSFragment;
import com.example.administrator.aitang.zhibo.education.fragment.tab.ChatRoomTabFragment;

/**
 * Created by Administrator on 2017/12/26.
 */

public class RTSTab extends ChatRoomTabFragment {
    private RTSFragment fragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCurrent();
    }

    @Override
    protected void onInit() {
        findViews();
    }

    @Override
    public void onCurrent() {
        super.onCurrent();
        if (fragment != null) {
            fragment.onCurrent();
        }
    }

    private void findViews() {
        if (fragment == null) {
            fragment = (RTSFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.rts_fragment);
        }
    }
}
