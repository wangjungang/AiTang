package com.example.administrator.aitang.ui.zhibo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.arialyy.annotations.Download;
import com.arialyy.annotations.DownloadGroup;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadGroupTask;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.AbsEntity;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.DownloadAdapter;
import com.example.administrator.aitang.ui.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//extends MyBaseActivity
public class MultiDownloadActivity  {
//    @BindView(R.id.list)
//    RecyclerView mList;
//    private DownloadAdapter mAdapter;
//    private List<AbsEntity> mData = new ArrayList<>();
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.activity_multi_download);
//    }
//
//    @Override
//    public void init() {
//        Aria.download(this).register();
//        setHeaderTitle("下载列表");
//        List<AbsEntity> temps = Aria.download(this).getTotleTaskList();
//        Log.d("TAG", "获取下载任务--temps==" + temps.toString());
//        if (temps != null && !temps.isEmpty()) {
//            mData.addAll(temps);
//        }
//        mAdapter = new DownloadAdapter(this, mData);
//        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.setAdapter(mAdapter);
//    }
//
//
//    @Download.onPre
//    void onPre(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskStart
//    void taskStart(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskResume
//    void taskResume(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskStop
//    void taskStop(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskCancel
//    void taskCancel(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskFail
//    void taskFail(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskComplete
//    void taskComplete(DownloadTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @Download.onTaskRunning()
//    void taskRunning(DownloadTask task) {
//        mAdapter.setProgress(task.getEntity());
//    }
//
//    //////////////////////////////////// 下面为任务组的处理 /////////////////////////////////////////
//
//    @DownloadGroup.onPre
//    void onGroupPre(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskStart
//    void groupTaskStart(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskResume
//    void groupTaskResume(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskStop
//    void groupTaskStop(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskCancel
//    void groupTaskCancel(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskFail
//    void groupTaskFail(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskComplete
//    void groupTaskComplete(DownloadGroupTask task) {
//        mAdapter.updateState(task.getEntity());
//    }
//
//    @DownloadGroup.onTaskRunning()
//    void groupTaskRunning(DownloadGroupTask task) {
//        mAdapter.setProgress(task.getEntity());
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //Aria.download(this).unRegister();
//    }
}
