package com.example.administrator.aitang.ui.mine;

import android.database.Cursor;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.annotations.DownloadGroup;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.download.DownloadGroupTask;
import com.arialyy.aria.core.inf.AbsEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.wode.download.DownloadAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.VideoCacheBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.FileUtils;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.permission.PermissionRequest;
import com.example.administrator.aitang.views.DividerItemDecoration;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoCacheActivity extends MyBaseActivity {

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.lv_video_cache)
    RecyclerView lvVideoCache;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.img_wuhuancun_video_cache)
    ImageView img_wuhuancun;
    private Cursor cursor;

    /**********************修改后*************************/
    private DownloadAdapter cacheAdapter;
    private List<AbsEntity> mData = new ArrayList<>();
    private boolean isAllStop = true;//当前是否为暂停状态
    private PermissionRequest permissionRequest;//权限请求工具类

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_video_cache);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        Aria.download(this).register();
        if (checkTaskisRuning()) {
            //当前有正在进行的任务，显示右上角为全部暂停
            changeAllTaskStateStop(false);
        } else {
            //当前无正在进行的任务，显示右上角为全部开始
            changeAllTaskStateStop(true);
        }
        requestPermissions();
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle(getString(R.string.course_cache), MyConstant.Position.CENTER, R.color.color_323232);
    }

    @OnClick({R.id.iv_header_left, R.id.tv_clear})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_header_left:
                VideoCacheActivity.this.finish();
                break;
            case R.id.tv_clear:

                //添加判断 如果当前全部已完成或者没有任务则提示不能点击
                if (checkIsComplete()) {
                    toast("当前没有可开始任务");
                    return;
                }

                //暂停所有或者开始所有，如果是true则开始
                if (isAllStop) {
                    Aria.download(this).resumeAllTask();
                    changeAllTaskStateStop(false);

                } else {
                    Aria.download(this).stopAllTask();
                    changeAllTaskStateStop(true);
                }

                break;
            default:
                break;
        }

    }

    /**
     * 请求权限，成功读取数据
     */
    private void requestPermissions() {
        if (null == permissionRequest) {
            permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
                @Override
                public void onSuccessful() {
                    getData();
                }

                @Override
                public void onFailure() {
                    //失败，如果是拒绝默认提示，如果点击不再提示，下次提示跳转设置
                }
            });
        }
        //请求读写内存权限
        permissionRequest.requestGroup(Permission.STORAGE);

    }

    /**
     * 加载数据
     */
    private void getData() {
        List<AbsEntity> temps = Aria.download(this).getTotleTaskList();

        List<DownloadGroupEntity> list = Aria.download(this).getGroupTaskList();
        if (temps != null && !temps.isEmpty()) {
            mData.addAll(temps);
        }
        cursor = createQuery();
        //展示的数据源
        List<VideoCacheBean> dataList = new ArrayList<>();
        //要移除的数据【文件在本地被删除】
        List<VideoCacheBean> removeDataList = new ArrayList<>();
        VideoCacheBean bean = null;
        if (null != cursor) {
            while (cursor.moveToNext()) {
                bean = new VideoCacheBean();
                bean.setFlagKey(cursor.getString(cursor
                        .getColumnIndex("flagKey")));
                bean.setTitle(cursor.getString(cursor
                        .getColumnIndex("className")));
                bean.setZhaiYao(cursor.getString(cursor
                        .getColumnIndex("zhaiyao")));
                bean.setTime(cursor.getString(cursor
                        .getColumnIndex("time")));
                bean.setTeacherName(cursor.getString(cursor
                        .getColumnIndex("name")));
                bean.setTeacherIntro(cursor.getString(cursor
                        .getColumnIndex("dis")));
                bean.setTeacherPicUrl(cursor.getString(cursor
                        .getColumnIndex("photo")));

                bean.setVideoName(cursor.getString(cursor
                        .getColumnIndex("videoName")));
                bean.setWhiteName(cursor.getString(cursor
                        .getColumnIndex("whiteName")));
                dataList.add(bean);
            }

            //循环将任务列表中的任务组装到本地数据中
            for (int i = 0; i < dataList.size(); i++) {

                String videoName = dataList.get(i).getVideoName();
                String url = dataList.get(i).getFlagKey();

                //如果文件不存在,删除数据库和下载库中的记录
                if (!FileUtils.fileIsExists(videoName)) {
                    //删除数据库
                    Myapp.fileProvider.delete("filetab", "flagKey=?", new String[]{url});

                    //删除下载库记录
                    List<DownloadGroupEntity> downList = Aria.download(this).getGroupTaskList();
                    if (null != downList) {
                        for (int j = 0; j < downList.size(); j++) {
                            List<String> urlList = downList.get(j).getUrls();
                            if (null != urlList && urlList.size() > 0) {
                                String[] urlStr = urlList.get(0).split("\\?");
                                if (urlStr[0].equals(url)) {
                                    Aria.download(this).load(downList.get(i)).cancel(true);
                                }
                            }
                        }
                    }

                    //移除本条数据
                    removeDataList.add(dataList.get(i));
                    Log.d("tag", "文件不存在，已删除记录");
                } else {

                    //如果文件存在，添加到显示的数据中
                    String localKey = dataList.get(i).getFlagKey();
                    for (int j = 0; j < mData.size(); j++) {
                        String downloadKey = mData.get(j).getStr();
                        if (!StringUtils.isEmpty(downloadKey) &&
                                !StringUtils.isEmpty(localKey) &&
                                downloadKey.equals(localKey)) {
                            dataList.get(i).setAbsEntity(mData.get(j));
                        }
                    }
                }
            }
            dataList.removeAll(removeDataList);
            //是否需要遍历一遍组装好的数据，删除AbsEntity为空的数据，保证数据同步

        }

        if (dataList.size() == 0) {
            lvVideoCache.setVisibility(View.GONE);
        } else {
            lvVideoCache.setVisibility(View.VISIBLE);
            cacheAdapter = new DownloadAdapter(this, dataList, new DownloadAdapter.TaskPauseOrRuningListenter() {
                @Override
                public void runing() {
                    changeAllTaskStateStop(false);
                }

                @Override
                public void pause() {
                    changeAllTaskStateStop(true);
                }
            });
            ((DefaultItemAnimator) lvVideoCache.getItemAnimator()).setSupportsChangeAnimations(false);
            lvVideoCache.setLayoutManager(new LinearLayoutManager(this));
            lvVideoCache.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            lvVideoCache.setAdapter(cacheAdapter);
        }
    }

    private Cursor createQuery() {
        String sql = "select * from filetab order by _id desc";
        Cursor cursor = Myapp.fileProvider.query(sql, null);
        return cursor;
    }


    /**
     * 改变右上按钮状态为暂停状态
     *
     * @param isStop true 当前显示为“全部开始”-实际状态是改变为暂停状态
     *               false 当前显示为“全部暂停”-实际状态是改变为下载中状态
     */
    private void changeAllTaskStateStop(boolean isStop) {

        if (isStop) {
            tvClear.setText("全部开始");
        } else {
            tvClear.setText("全部暂停");
        }
        isAllStop = isStop;
    }

    /**
     * 检查当前是否有任务在进行
     *
     * @return 返回true 说明有下载中的任务
     */
    private boolean checkTaskisRuning() {
        boolean isRuning = false;
        List<AbsEntity> temps = Aria.download(this).getTotleTaskList();

        if (null == temps || temps.size() == 0) {
            return false;
        }

        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i).getState() == IEntity.STATE_RUNNING) {
                isRuning = true;
                break;
            }
        }
        return isRuning;
    }

    /**
     * 判断当前全部已完成或者没有任务
     *
     * @return 返回true
     */
    private boolean checkIsComplete() {
        boolean isComplete = true;
        List<AbsEntity> temps = Aria.download(this).getTotleTaskList();

        if (null == temps || temps.size() == 0) {
            return true;
        }

        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i).getState() != IEntity.STATE_COMPLETE) {
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
    }

    //////////////////////////////////// 下面为任务组的处理 /////////////////////////////////////////

    @DownloadGroup.onPre
    void onGroupPre(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskStart
    void groupTaskStart(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskResume
    void groupTaskResume(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskStop
    void groupTaskStop(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskCancel
    void groupTaskCancel(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskFail
    void groupTaskFail(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskComplete
    void groupTaskComplete(DownloadGroupTask task) {
        cacheAdapter.updateState(task.getEntity());
        if (!checkTaskisRuning()) {
            changeAllTaskStateStop(true);
        }
    }

    @DownloadGroup.onTaskRunning()
    void groupTaskRunning(DownloadGroupTask task) {
        cacheAdapter.setProgress(task.getEntity());
    }
}
