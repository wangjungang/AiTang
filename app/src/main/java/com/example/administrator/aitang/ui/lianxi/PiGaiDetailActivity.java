package com.example.administrator.aitang.ui.lianxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.PiGaiDetailBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.PiGaiDetailFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PiGaiDetailActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_right)
    ImageView iv_right;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.vp_pigai)
    ViewPager vp;

    List<PiGaiDetailBean.DataBean> datas = new ArrayList<>();//习题

    private int currentPageIndex = 0;//当前题目的id
    public static List<PiGaiDetailFragment> pigaiFragments;
    private PiGaiDetailFragment fragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pi_gai_result);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        iv_left.setImageResource(R.drawable.back_icon_nav);
        iv_left.setVisibility(View.VISIBLE);
        tv_title.setText("批改详情");
        iv_right.setImageResource(R.drawable.gengduo_icon_default_cuotifenxi);
        iv_right.setVisibility(View.GONE);

        requestDetail();
    }

    /**
     * 请求详情
     */
    private void requestDetail() {

        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type", "");
        String questionid = bundle.getString("questionid", "");

        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", type);
        paramsMap.put("questionid", questionid);

        HttpManager.getInstance().get(MyConstant.MESSAGEDETAIL, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {


                PiGaiDetailBean piGaiDetailBean = new Gson().fromJson(response, PiGaiDetailBean.class);
                datas.clear();
                datas.addAll(piGaiDetailBean.getData());
                initFragemnt();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {

                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
            }
        });

    }


    private void initFragemnt() {
        pigaiFragments = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            fragment = new PiGaiDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", datas.get(i));//习题
            bundle.putInt("index", i);//习题下标
            fragment.setArguments(bundle);
            //把fragemnt添加到Fragemnt集合中
            fragment.setNum(i);//这只当前题目的下标，用来获取相应答案
            pigaiFragments.add(fragment);
        }
        PiGaiDetailActivity.MyAdapter adapter = new PiGaiDetailActivity.MyAdapter(PiGaiDetailActivity.this.getSupportFragmentManager());
        vp.setAdapter(adapter);
        // 设置ViewPager的监听事件
        vp.addOnPageChangeListener(this);
        onPageSelected(0);
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.iv_header_right:
//                getPopuwindow();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPageIndex = position;
        tv_type.setText((position + 1) + "/" + datas.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return pigaiFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pigaiFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
