package com.example.administrator.aitang.ui.zhibo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.TeacherPingjiaAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.TeacherDetailBean;
import com.example.administrator.aitang.bean.zhibo.TeacherPingjia;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.RequestFailResult;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.builder.GetBuilder;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.example.administrator.aitang.views.MyListView;
import com.example.administrator.aitang.views.MyRatingBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PingjiaActivity extends MyBaseActivity {
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_pingjiaAct_content)
    TextView tvPingjiaActContent;
    @BindView(R.id.tv_pingjiaAct_xingji)
    MyRatingBar tvPingjiaActXingji;
    @BindView(R.id.tv_pingjiaAct_fenshu)
    TextView tvPingjiaActFenshu;
    @BindView(R.id.tv_pingjiaAct_canyupingjia)
    TextView tvPingjiaActCanyupingjia;
    @BindView(R.id.rl_write_comment)
    RelativeLayout rlWriteComment;
    @BindView(R.id.mlsv_pingjiaAct)
    MyListView mlsvPingjiaAct;
    TeacherPingjiaAdapter adapter;
    List<TeacherPingjia.DataBean.CommentBean> datas;
    private String tid;
    private String cdid;
    String cid = null;//请求的时候如果带上cid用于分页，cid从最近一次的请求最后一个数据中获取

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pingjia);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivHeaderLeft.setImageResource(R.drawable.back_icon_nav);
        ivHeaderLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvHeaderTitle.setText("评价");
        Bundle bundle = getIntent().getExtras();
        TeacherDetailBean.DataBean data = bundle.getParcelable("data");
        tid = data.getTid();
        cdid = data.getCdid();
        tvPingjiaActContent.setText(data.getTname() + "-" + data.getCdintro() + "-" + data.getFather());
        float num = (float) data.getScore() / 20;
        DecimalFormat df = new DecimalFormat("0.0");
        tvPingjiaActXingji.setStar(Float.parseFloat(df.format(num)));
        tvPingjiaActFenshu.setText(data.getScore() + "分");
        datas = new ArrayList<>();
        adapter = new TeacherPingjiaAdapter(this, datas);
        mlsvPingjiaAct.setAdapter(adapter);
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.startRefresh();
    }

    @Override
    public void refresh() {
        Log.d("PingjiaActivity", "refresh");
        requestData(null);
    }

    private void requestData(String pageNum) {
        Log.d("PingjiaActivity", "requestdata");
//        if (!BeforeNetRequestCheckUtil.isNetAndLogin(this))
//            return;
        GetBuilder request = OkHttpUtils.get().url(MyConstant.CLASS_COMMENT)
                .addParams("uid", Myapp.spUtil.getUid())
                .addParams("token", Myapp.spUtil.getToken())
                .addParams("tid", tid)
                .addParams("cdid", cdid);
        if (pageNum != null)
            request.addParams("cid", pageNum);
        request.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("TAG", "请求老师数据错误" + e);
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                Log.d("TAG", "请求老师数据" + response);
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
                JSONObject object = new JSONObject(response);
                String code = object.getString("code");

                if (code.equals("200")) {
                    TeacherPingjia bean = new Gson().fromJson(response, TeacherPingjia.class);
                    tvPingjiaActCanyupingjia.setText(bean.getData().getComment().size() + "人参与评价");
                    adapter.addAll(bean.getData().getComment(), true);
                    if (bean.getData().getIscomment().equals("1")) {
                        rlWriteComment.setVisibility(View.INVISIBLE);
                    }else {
                        rlWriteComment.setVisibility(View.VISIBLE);
                    }
                } else  if (code.equals("201")){
                    rlWriteComment.setVisibility(View.VISIBLE);
                }else {
                    RequestFailResult.requestFail(code);
                }
            }
        });
    }

    @Override
    public void loadMore() {
        if (cid != null)
            requestData(cid);
    }

    @OnClick({R.id.iv_header_left, R.id.rl_write_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.rl_write_comment:
                Intent intent = new Intent(this, WriteCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid", tid);
                bundle.putString("cdid", cdid);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            default:
                break;
        }

    }


}
