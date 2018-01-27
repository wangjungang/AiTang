package com.example.administrator.aitang.adapter.wode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.wode.AiTangBroadcastBean;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : wangzexin
 * Date : 2017/11/9
 * Describe : 爱唐播报 列表适配器
 */

public class AiTangBroadcastAdapter extends MyBaseAdapter<AiTangBroadcastBean.DataBean> {


    public AiTangBroadcastAdapter(Context context, List<AiTangBroadcastBean.DataBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_aitangbroadcast, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String title = getItem(i).getPdtitle();
        String intro = getItem(i).getPdintro();
        String time = getItem(i).getTime();
        String coverUrl = getItem(i).getPdurl();
        if (null != title) holder.tvTitle.setText(title);
        if (null != intro) holder.tvIntro.setText(intro);
        if (!StringUtils.isEmpty(time)) {
            String showTime = DateUtil.toString(Long.parseLong(time), "yyyy.MM.dd");
            holder.tvTime.setText(showTime);
        }

        if (!StringUtils.isEmpty(coverUrl)) {
            GlideUtils.setAvatar(coverUrl, holder.imgCover);
        }

        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_cover)
        ImageView imgCover;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
