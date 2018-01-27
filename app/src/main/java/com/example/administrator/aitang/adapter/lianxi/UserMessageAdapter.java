package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.UserMessageBean;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户消息适配器
 */

public class UserMessageAdapter extends MyBaseAdapter<UserMessageBean.DataBean> {


    public UserMessageAdapter(Context context, List<UserMessageBean.DataBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_usermessage, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        UserMessageBean.DataBean bean = getItem(i);
        String title = bean.getMessageintro();
        String time = bean.getTime();

        if (!StringUtils.isEmpty(title)) {
            viewHolder.tvTitle.setText(title);
        }

        if (!StringUtils.isEmpty(time)) {
            String showTime = DateUtil.toString(Long.parseLong(time), "yyyy.MM.dd");
            viewHolder.tvTime.setText(showTime);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
