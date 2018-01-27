package com.example.administrator.aitang.ui.zhibo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.TeacherDetailAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.bean.zhibo.TeacherDetailBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.RequestFailResult;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.example.administrator.aitang.views.MyListView;
import com.example.administrator.aitang.views.MyRatingBar;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MingshiXinxiActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_mingshixinxiAct_pic)
    ImageView tvMingshixinxiActPic;
    @BindView(R.id.tv_mingshixinxiAct_name)
    TextView tvMingshixinxiActName;
    @BindView(R.id.tv_mingshixinxiAct_dis)
    TextView tvMingshixinxiActDis;
    @BindView(R.id.tv_mingshixinxiAct_xingji)
    MyRatingBar tvMingshixinxiActXingji;
    @BindView(R.id.tv_mingshixinxiAct_fenshu)
    TextView tvMingshixinxiActFenshu;
    @BindView(R.id.tv_mingshixinxiAct_content)
    TextView tvMingshixinxiActContent;
    @BindView(R.id.mlsv_mingshixinxiAct)
    MyListView mlsvMingshixinxiAct;
    ClassBean.DataBean.TeacherBean data;
    TeacherDetailAdapter adapter;
    List<TeacherDetailBean.DataBean> datas;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mingshi_xinxi);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivHeaderLeft.setImageResource(R.drawable.back_icon_nav);
        ivHeaderLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvHeaderTitle.setText("名师信息");
        data = getIntent().getParcelableExtra("data");
        datas = new ArrayList<>();
        adapter = new TeacherDetailAdapter(this, datas);
        mlsvMingshixinxiAct.setAdapter(adapter);
        if (data != null) {
            GlideUtils.setCircleAvatar(data.getTpic(), tvMingshixinxiActPic, R.drawable.touxiang_image_laoshijieshao, R.drawable.touxiang_image_laoshijieshao);
            tvMingshixinxiActName.setText(data.getTname());
            tvMingshixinxiActDis.setText(data.getTsimple());
            float num = (float) Float.parseFloat(data.getTscore()) / 20;
            DecimalFormat df = new DecimalFormat("0.0");
            tvMingshixinxiActXingji.setStar(Float.parseFloat(df.format(num)));
            tvMingshixinxiActFenshu.setText(data.getTscore());
            tvMingshixinxiActContent.setText(data.getTintro());
            String id = data.getTid();//老师的id
            mlsvMingshixinxiAct.setOnItemClickListener(this);
            OkHttpUtils.get().url(MyConstant.CLASS_TEACHERDETAIL)
                    .addParams("uid", Myapp.spUtil.getUid())
                    .addParams("token", Myapp.spUtil.getToken())
                    .addParams("tid", id)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.d("MingshiXinxiActivity", "请求数据出错" + e);
                }

                @Override
                public void onResponse(String response, int id) throws IOException, JSONException {

                    Log.d("MingshiXinxiActivity", "请求数据成功" + response);
                    TeacherDetailBean bean = new Gson().fromJson(response, TeacherDetailBean.class);
                    if (bean.getCode().equals("200")) {
                        adapter.addAll(bean.getData(), false);
                    } else {
                        RequestFailResult.requestFail(bean.getCode());
                    }
                }
            });
        }

    }


    @OnClick(R.id.iv_header_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TeacherDetailBean.DataBean bean = adapter.getItem(i);
        Intent intent = new Intent(this, PingjiaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", bean);
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }
}
