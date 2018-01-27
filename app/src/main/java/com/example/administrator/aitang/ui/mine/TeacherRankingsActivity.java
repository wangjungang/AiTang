package com.example.administrator.aitang.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.wode.TeacherRankAdapter;
import com.example.administrator.aitang.bean.wode.TeacherRankBean;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.ui.zhibo.MingshiXinxiActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TeacherRankingsActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.lv_teacher_rank)
    ListView lvTeacherRank;


    private TeacherRankAdapter teacherRankAdapter = null;
    private List<TeacherRankBean.DataBean> teacherRankList = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_teacher_rankings);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
//        lvTeacherRank.setOnItemClickListener(this);
        teacherRankAdapter = new TeacherRankAdapter(TeacherRankingsActivity.this, teacherRankList);
        lvTeacherRank.setAdapter(teacherRankAdapter);
        lvTeacherRank.setOnItemClickListener(this);
        setRefresh();


    }


    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle(getString(R.string.teacher_rankings), MyConstant.Position.CENTER, R.color.color_323232);
    }

    /**
     * 设置刷新相关
     */
    private void setRefresh() {
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.startRefresh();
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

    @Override
    public void refresh() {
        super.refresh();
        requestData();
    }

    @OnClick({R.id.iv_header_left})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_header_left:
                TeacherRankingsActivity.this.finish();
                break;
            default:
                break;
        }

    }


    /**
     * 请求数据
     */
    private void requestData() {

        HttpManager.getInstance().get(MyConstant.TEACHERRANK, null, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                TeacherRankBean teacherRankBean = new Gson().fromJson(response, TeacherRankBean.class);

                if (null != teacherRankBean) {
                    teacherRankList.clear();
                    teacherRankList.addAll(teacherRankBean.getData());
                    teacherRankAdapter.notifyDataSetChanged();
                }
                stopRefresh();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                stopRefresh();
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {

                } else {
                    toast(ErrorDes.ERROR_INFO);
                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        TeacherRankBean.DataBean dataBean = teacherRankList.get(i);
//        ClassBean.DataBean.TeacherBean intentBean = new ClassBean.DataBean.TeacherBean();
//
//        intentBean.setTid(dataBean.getTid());
//        intentBean.setTintro(dataBean.getTintro());
//        intentBean.setTname(dataBean.getTname());
//        intentBean.setTpic(dataBean.getTpic());
//        intentBean.setTscore(dataBean.getTscore());
//        intentBean.setTsimple(dataBean.getTsimple());
//
//        Intent intent = new Intent(this, MingshiXinxiActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("data", intentBean);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }
}
