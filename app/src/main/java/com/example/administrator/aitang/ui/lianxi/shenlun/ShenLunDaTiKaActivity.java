package com.example.administrator.aitang.ui.lianxi.shenlun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DatikaAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShenLunBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.shenlun.ShenLunKSZNLXFragment;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyGridView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunActivity.shenLunData;

public class ShenLunDaTiKaActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_header_right)
    ImageView iv_right;
    @BindView(R.id.tv_datiAct_title)
    TextView tv_type;
    @BindView(R.id.gv_datiAct)
    MyGridView gv;
    @BindView(R.id.btn_datiAct_tijiao)
    Button btn_tijiao;
    List<Integer> datas;
    DatikaAdapter adapter;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private int sum;//题目的总数量
    private long uptimes_start;//试题开始的时间

    private int timuCount;//做的题数
    private String totalPrice = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_da_ti_ka_shenlun);
    }

    @Override
    public void init() {
        Myapp.xiTiActivities.add(this);
        setImmerBarcolor(llHeader);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("答题卡");
        //请求焦点，防止界面抖动
        tv_type.setFocusableInTouchMode(true);
        tv_type.setFocusable(true);

        tv_type.setText("申论专项批改（需购买）");


        Bundle bundle = getIntent().getExtras();
        sum = shenLunData.size();
        uptimes_start = bundle.getLong("uptimes_start");
        datas = new ArrayList<>();
        adapter = new DatikaAdapter(this, datas) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = super.getView(i, view, viewGroup);
                ViewHolder holder = (ViewHolder) v.getTag();
                ShenLunBean.DataBean dataBean = shenLunData.get(i);


                //如果答案为空，并且照片也为空，则认为这道题没有做
                if (StringUtils.isTrimEmpty(dataBean.getAnswerContent())
                        && (null == dataBean.getImgPathList() || dataBean.getImgPathList().size() == 0)) {
                    holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);

                } else {//否则就是做了，计算收费
                    holder.tv_tiNum.setBackgroundResource(R.drawable.yizuo_image_datika);
                }
                return v;
            }
        };
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
        for (int i = 0; i < sum; i++) {
            adapter.add((i + 1));
        }

        //计算做题信息，题数和价格
        initZuoTiInfo();
    }

    /**
     * 计算做题信息，题数和价格
     */
    private void initZuoTiInfo() {

        timuCount = 0;
        BigDecimal totalFee = new BigDecimal("0");

        for (int i = 0; i < shenLunData.size(); i++) {
            ShenLunBean.DataBean dataBean = shenLunData.get(i);

            //如果答案为空，并且照片也为空，则认为这道题没有做，否则就是做了，计算收费
            if (StringUtils.isTrimEmpty(dataBean.getAnswerContent())
                    && (null == dataBean.getImgPathList() || dataBean.getImgPathList().size() == 0)) {

            } else {//否则就是做了，计算收费
                timuCount++;

                String price = dataBean.getPrice();

                //如果价格不为空，计算费用
                if (!StringUtils.isEmpty(price)) {
                    BigDecimal a1 = new BigDecimal(price);
                    totalFee = totalFee.add(a1);
                }
            }

        }

        totalPrice = String.valueOf(totalFee);

        tvCount.setText(String.valueOf(timuCount));
        if (!StringUtils.isEmpty(totalPrice)) {
            tvPrice.setText(totalPrice);
        } else {
            tvPrice.setText("0");
        }

    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right, R.id.btn_datiAct_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.btn_datiAct_tijiao://提交
                //跳转提交
                startTiJiaoIntent();

                break;
        }
    }


    /**
     * 跳转解析的Intent
     */
    private void startTiJiaoIntent() {

        //TODO 下订单，付款，提交成功关闭页面，没有解析界面
        Intent intent = new Intent(this, ShenLunTijiaoDingDanActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ShenLunActivity.vp_KsznlxAct.setCurrentItem(i);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ShenLunKSZNLXFragment.REQUEST_CODE_JIEXI && resultCode == ShenLunKSZNLXFragment.RESULT_CODE_JIEXI) {
            Myapp.clearXiTiActivities();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.xiTiActivities.remove(this);
    }
}
