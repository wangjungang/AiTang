package com.example.administrator.aitang.ui.lianxi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.ShitiTypeAdapter;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class ShitiTypeActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.lsv_shiti_type)
    ListView listView;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.headerview)
    LinearLayout ll_header;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shiti_type);

    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(ll_header);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        ShouYeBean.DataBean.QuetionsBean questions = getIntent().getExtras().getParcelable("questions");
        String title = questions.getQtname();
        tv_title.setText(title);
        List<ShouYeBean.DataBean.QuetionsBean.ChildBean> childList = new ArrayList<>();
        if (null != questions.getChild()) {
            childList.addAll(questions.getChild());
        }
        ShitiTypeAdapter adapter = new ShitiTypeAdapter(this, childList);
        listView.setAdapter(adapter);
        ivHeaderLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            default:
                break;
        }
    }

}
