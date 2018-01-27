package com.example.administrator.aitang.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.wode.CourserCouponAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.CashCouponBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CoursenotesActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.headerview)
    LinearLayout llIncludeheader;
    @BindView(R.id.course_tip)
    TextView courseTip;
    @BindView(R.id.lv_course_coupon)
    ListView lvCourseCoupon;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private CourserCouponAdapter courserCouponAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        setImmerBarcolor(llIncludeheader);
        setHeader();
        setRefresh();
        lvCourseCoupon.setOnItemClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setHeaderTitle("课程券", MyConstant.Position.CENTER, R.color.color_323232);
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
        getMineCoupon();
    }

    /**
     * 获取代金券
     */
    private void getMineCoupon() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().post(MyConstant.GETMINECOUPON, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                CashCouponBean cashCouponBean = new Gson().fromJson(response, CashCouponBean.class);
//                课程券
                List<CashCouponBean.DataBean.ClassBean> classBeanList = cashCouponBean.getData().getClassX();

//                //                //模拟一下
//                List<CashCouponBean.DataBean.ClassBean> classBeanList = new ArrayList<CashCouponBean.DataBean.ClassBean>();
//                CashCouponBean.DataBean.ClassBean classBean = new CashCouponBean.DataBean.ClassBean();
//                classBean.setNumber("3");
//                classBean.setUcprice("100");
//                CashCouponBean.DataBean.ClassBean classBean1 = new CashCouponBean.DataBean.ClassBean();
//                classBean1.setNumber("4");
//                classBean1.setUcprice("300");
//                CashCouponBean.DataBean.ClassBean classBean2 = new CashCouponBean.DataBean.ClassBean();
//                classBean2.setNumber("6");
//                classBean2.setUcprice("500");
//                classBeanList.add(classBean);
//                classBeanList.add(classBean1);
//                classBeanList.add(classBean2);
//                classBeanList.add(classBean2);
//                classBeanList.add(classBean2);
//                classBeanList.add(classBean2);
//                classBeanList.add(classBean2);
//                classBeanList.add(classBean2);


                //显示课程券
                showClassCoupon(classBeanList);
                stopRefresh();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {

                toast(result.getDesc());
                stopRefresh();
                //TODO 建议展示一个请求错误状态View,点击可以再请求
            }
        });

    }

    /**
     * 设置
     */
    private void showClassCoupon(List<CashCouponBean.DataBean.ClassBean> classBeanList) {

        if (null == classBeanList || classBeanList.size() == 0) {

            //TODO 建议展示空View,并return
            showEmptyView(true);
            return;
        }
        showEmptyView(false);
        courserCouponAdapter = new CourserCouponAdapter(CoursenotesActivity.this, classBeanList);
        lvCourseCoupon.setAdapter(courserCouponAdapter);
        setListViewHeightBasedOnChildren(lvCourseCoupon);

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        CourserCouponAdapter listAdapter = (CourserCouponAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 是否展示空View
     *
     * @param isShow true展示空View false展示listView
     */
    private void showEmptyView(boolean isShow) {
        if (isShow) {
            tvEmpty.setVisibility(View.VISIBLE);
            lvCourseCoupon.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            lvCourseCoupon.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coursenotes);
    }

    @OnClick(R.id.iv_header_left)
    public void onViewClicked() {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CashCouponBean.DataBean.ClassBean bean = courserCouponAdapter.getItem(i);
        String price = bean.getUcprice();
        Intent intent = new Intent();
        intent.putExtra("price", price);
        intent.putExtra("ucid",bean.getUcid());
        setResult(100, intent);
        finish();
    }
}
