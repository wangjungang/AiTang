package com.example.administrator.aitang.adapter.wode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoCacheAdapter extends MyBaseAdapter<String> {

    Context mContext;

    public VideoCacheAdapter(Context context, List<String> datasource) {
        super(context, datasource);
        mContext = context;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_video_cache, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final String bean = getItem(i);

//        holder.tvTitle.setText(bean.getTname());
//        holder.tvIntro.setText(bean.getTname());
//        holder.tvTime.setText(bean.getTname());
//        holder.tvTeacherName.setText(bean.getTname());
//        holder.tvTeacherIntro.setText(bean.getTname());
//        GlideUtils.setCircleAvatar(url, holder.ivHead, R.drawable.touxiang_image_laoshijieshao, R.drawable.touxiang_image_laoshijieshao);


        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 播放事件
//                mContext.startActivity(new Intent(mContext, PlayBackActivity.class));
                String roomid = "19743991";//我的聊天室id
//                PlayBackActivity.start(mContext);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //在ListView里，点击侧滑菜单上的选项时，如果想让擦花菜单同时关闭，调用这句话
                ((SwipeMenuLayout) holder.slConvertView).quickClose();
                remove(getItem(i));
                notifyDataSetChanged();
            }
        });


        return view;
    }

    static class ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_teacher_name)
        TextView tvTeacherName;
        @BindView(R.id.tv_teacher_intro)
        TextView tvTeacherIntro;
        @BindView(R.id.btn_play)
        ImageView btnPlay;
        @BindView(R.id.btnDelete)
        Button btnDelete;
        @BindView(R.id.sl_convertView)
        SwipeMenuLayout slConvertView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
