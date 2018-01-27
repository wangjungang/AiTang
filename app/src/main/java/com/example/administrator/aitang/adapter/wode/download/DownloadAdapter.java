/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.aitang.adapter.wode.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.inf.AbsEntity;
import com.arialyy.aria.core.inf.AbsTaskEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.VideoCacheBean;
import com.example.administrator.aitang.ui.mine.PlayBackActivity;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.views.HorizontalProgressBarWithNumber;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;

/**
 * Created by Lyy on 2016/9/27.
 * 下载列表适配器
 * https://github.com/AriaLyy/Aria
 */
public class DownloadAdapter extends AbsRVAdapter<VideoCacheBean, DownloadAdapter.SimpleHolder> {
    private static final String TAG = "DownloadAdapter";
    private Map<String, Integer> mPositions = new ConcurrentHashMap<>();
    private Context mContext;

    //回调到activity的接口
    private TaskPauseOrRuningListenter taskPauseOrRuningListenter;

    public interface TaskPauseOrRuningListenter {
        void runing();

        void pause();

    }

    public DownloadAdapter(Context context, List<VideoCacheBean> data, TaskPauseOrRuningListenter taskPauseOrRuningListenter) {
        super(context, data);
        this.mContext = context;
        this.taskPauseOrRuningListenter = taskPauseOrRuningListenter;
        for (int j = 0; j < data.size(); j++) {
            mPositions.put(getKey(data.get(j).getAbsEntity()), j);
        }
    }

    private String getKey(AbsEntity entity) {
        if (entity instanceof DownloadEntity) {
            return ((DownloadEntity) entity).getUrl();
        } else if (entity instanceof DownloadGroupEntity) {
            return ((DownloadGroupEntity) entity).getGroupName();
        }
        return "";
    }

    @Override
    protected SimpleHolder getViewHolder(View convertView, int viewType) {
        if (viewType == 1) return new SimpleHolder(convertView);
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    protected int setLayoutId(int type) {
        return R.layout.inflate_lsvitem_video_cache;
    }


    public synchronized void updateState(AbsEntity entity) {
        if (entity.getState() == IEntity.STATE_CANCEL) {
            mData.remove(entity);
            mPositions.clear();
            int i = 0;

            for (int j = 0; j < mData.size(); j++) {
                mPositions.put(getKey(mData.get(j).getAbsEntity()), i);
            }
            notifyDataSetChanged();
        } else {
            int position = indexItem(getKey(entity));
            if (position == -1 || position >= mData.size()) {
                return;
            }
            mData.get(position).setAbsEntity(entity);
            notifyItemChanged(position);
        }
    }

    /**
     * 更新进度
     */
    public synchronized void setProgress(AbsEntity entity) {
        String url = entity.getKey();
        int position = indexItem(url);
        if (position == -1 || position >= mData.size()) {
            return;
        }
        mData.get(position).setAbsEntity(entity);
        notifyItemChanged(position);
    }

    private synchronized int indexItem(String url) {
        Set<String> keys = mPositions.keySet();
        for (String key : keys) {
            if (key.equals(url)) {
                return mPositions.get(key);
            }
        }
        return -1;
    }

    @Override
    protected void bindData(SimpleHolder holder, int position, VideoCacheBean item) {
        handleProgress(holder, position, item.getAbsEntity());
    }


    @SuppressLint("SetTextI18n")
    private void handleProgress(final SimpleHolder holder, final int position, final AbsEntity entity) {
        holder.progress.setVisibility(View.VISIBLE);
        holder.btnPlay.setEnabled(false);
        String str = "";
        int color = android.R.color.holo_green_light;
        switch (entity.getState()) {
            case IEntity.STATE_WAIT:
            case IEntity.STATE_OTHER:
            case IEntity.STATE_FAIL:
                str = "开始";
                break;
            case IEntity.STATE_STOP:
                str = "恢复";
                color = android.R.color.holo_blue_light;
                break;
            case IEntity.STATE_PRE:
            case IEntity.STATE_POST_PRE:
            case IEntity.STATE_RUNNING:
                str = "暂停";
                color = android.R.color.holo_red_light;
                break;
            case IEntity.STATE_COMPLETE:
                str = "完成";
                holder.btnPlay.setEnabled(true);
                holder.progress.setVisibility(View.INVISIBLE);
                break;
        }


        long size = entity.getFileSize();
        long progress = entity.getCurrentProgress();
        int current = size == 0 ? 0 : (int) (progress * 100 / size);

        holder.progress.setProgress(current);
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvIntro.setText("课程摘要：" + mData.get(position).getZhaiYao());
        holder.tvTime.setText(mData.get(position).getTime());
        holder.tvTeacherName.setText(mData.get(position).getTeacherName());
        holder.tvTeacherIntro.setText(mData.get(position).getTeacherIntro());
        GlideUtils.setCircleAvatar(mData.get(position).getTeacherPicUrl(), holder.ivHead, R.drawable.touxiang_image_laoshijieshao, R.drawable.touxiang_image_laoshijieshao);


        //播放按钮点击事件
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                跳转到回放页面，传递过去播放路径，播放类型（白板/视频）
                final String videoName = mData.get(position).getVideoName();
                final String whiteName = mData.get(position).getWhiteName();
                PlayBackActivity.start(mContext, videoName, whiteName);

            }
        });

        //行点击事件
        holder.llItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (entity.getState()) {
                    case IEntity.STATE_WAIT:
                    case IEntity.STATE_OTHER:
                    case IEntity.STATE_FAIL:
                    case IEntity.STATE_STOP:
                    case IEntity.STATE_PRE:
                    case IEntity.STATE_POST_PRE:
                        start(entity);
                        Toast.makeText(Myapp.getAppContext(), "开始下载", Toast.LENGTH_SHORT).show();

                        taskPauseOrRuningListenter.runing();
                        break;
                    case IEntity.STATE_RUNNING:
                        stop(entity);
                        Toast.makeText(Myapp.getAppContext(), "暂停下载", Toast.LENGTH_SHORT).show();

                        taskPauseOrRuningListenter.pause();
                        break;
                    case IEntity.STATE_COMPLETE:
                        Log.d(TAG, "任务已完成");
                        break;
                }
            }
        });

        //左划删除点击事件
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在ListView里，点击侧滑菜单上的选项时，如果想让擦花菜单同时关闭，调用这句话
                ((SwipeMenuLayout) holder.slConvertView).quickClose();
                mData.remove(mData.get(position));
                cancel(entity);
                Myapp.fileProvider.delete("filetab", "flagKey=?", new String[]{entity.getKey()});
                notifyDataSetChanged();
                Toast.makeText(Myapp.getAppContext(), "删除", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void cancel(AbsEntity entity) {
        switch (entity.getTaskType()) {
            case AbsTaskEntity.D_FTP:
                Aria.download(getContext())
                        .loadFtp((DownloadEntity) entity)
                        //.login("lao", "123456")
                        .cancel(true);
                break;
            case AbsTaskEntity.D_FTP_DIR:
                break;
            case AbsTaskEntity.D_HTTP:
                Aria.download(getContext()).load((DownloadEntity) entity).cancel(true);
                break;
            case AbsTaskEntity.DG_HTTP:
                Aria.download(getContext()).load((DownloadGroupEntity) entity).cancel(true);
                break;
        }
    }

    private void start(AbsEntity entity) {
        switch (entity.getTaskType()) {
            case AbsTaskEntity.D_FTP:
                //Aria.download(getContext()).loadFtp((DownloadEntity) entity).login("lao", "123456").start();
                Aria.download(getContext()).loadFtp((DownloadEntity) entity).charSet("GBK").start();
                break;
            case AbsTaskEntity.D_FTP_DIR:
                break;
            case AbsTaskEntity.D_HTTP:
                Aria.download(getContext()).load((DownloadEntity) entity).start();
                break;
            case AbsTaskEntity.DG_HTTP:
                Aria.download(getContext()).load((DownloadGroupEntity) entity).start();
                break;
        }
    }

    private void stop(AbsEntity entity) {
        switch (entity.getTaskType()) {
            case AbsTaskEntity.D_FTP:
                //Aria.download(getContext()).loadFtp((DownloadEntity) entity).login("lao", "123456").stop();
                Aria.download(getContext()).loadFtp((DownloadEntity) entity).charSet("GBK").stop();
                break;
            case AbsTaskEntity.D_FTP_DIR:
                break;
            case AbsTaskEntity.D_HTTP:
                Aria.download(getContext()).load((DownloadEntity) entity).stop();
                break;
            case AbsTaskEntity.DG_HTTP:
                Aria.download(getContext()).load((DownloadGroupEntity) entity).stop();
                break;
        }
    }

    class SimpleHolder extends AbsHolder {
        @BindView(R.id.progress)
        HorizontalProgressBarWithNumber progress;
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

        @BindView(R.id.ll_itemview)
        LinearLayout llItemView;

        SimpleHolder(View itemView) {
            super(itemView);
        }
    }

}