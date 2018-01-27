package com.example.administrator.aitang.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.account.ExameTypeAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.ExameTypeBean;
import com.example.administrator.aitang.constant.MyConstant;
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

/**
 * Created by 武培培 on 2017/10/21.
 * 考试类型
 */

public class ExameTypeActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.lsv_exame)
    ListView listView;
    @BindView(R.id.iv_header_left)
    ImageView iv_back;
    @BindView(R.id.tv_header_right)
    TextView tv_right;
    List<ExameTypeBean.Data> datas = new ArrayList<>();
    ExameTypeAdapter adapter;
    private String parentid;

    private String flag = "";//跳转过来的标志，1为注册界面跳转 2为首页练习和个人设置
    private Bundle bundle;

    @Override
    public void setContentView() {
        setContentView(R.layout.exam_type_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.shitiTypeActivities.remove(this);
    }

    @Override
    public void init() {
        super.init();

        Myapp.shitiTypeActivities.add(this);
        setImmerBarcolor(llHeaderView);

        bundle = getIntent().getExtras();
        flag = bundle.getString("flag");

        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.startRefresh();
        adapter = new ExameTypeAdapter(this, datas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parentid = datas.get(i).getTestid();
                adapter.changCheckItemn(String.valueOf(i));
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();
        requestExameType();
    }


    private void requestExameType() {

        Map paramsMap = new HashMap();
        HttpManager.getInstance().get(MyConstant.EXAMETYPE, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ExameTypeBean bean = new Gson().fromJson(response, ExameTypeBean.class);
                datas = bean.getData();
                if (datas.size() > 0) {
                    adapter.addAll(datas, true);
                    adapter.notifyDataSetChanged();
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());

                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {
                    toast("没有考试信息");
                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_header_right:
                if (TextUtils.isEmpty(parentid)) {
                    toast("请选择考试类型");
                } else {
                    Intent intent = new Intent(ExameTypeActivity.this, ExameTypeAddressActivity.class);
                    bundle.putString("prentid", parentid);//不用重新创建bundle,直接用上个页面的
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
