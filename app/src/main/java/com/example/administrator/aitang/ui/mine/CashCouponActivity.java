package com.example.administrator.aitang.ui.mine;

import android.view.View;
import android.view.ViewGroup;
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
import com.example.administrator.aitang.http.error.ErrorDes;
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

public class CashCouponActivity extends MyBaseActivity {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.lv_course_coupon)
    ListView lvCourseCoupon;
    @BindView(R.id.tv_cash_coupons)
    TextView tvCashCoupons;
    @BindView(R.id.tv_sign_days)
    TextView tvSignDays;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private CourserCouponAdapter courserCouponAdapter = null;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_cash_coupon);

    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        setRefresh();
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle(getString(R.string.cash_coupon), MyConstant.Position.CENTER, R.color.color_323232);
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

    @OnClick({R.id.iv_header_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;
            default:
                break;
        }
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

                //显示课程券
                showClassCoupon(classBeanList);

                //现金券和签到天数
                CashCouponBean.DataBean.AllBean allBean = cashCouponBean.getData().getAll();

                //显示现金数和签到天数
                showDaysAndPrice(allBean);
                stopRefresh();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(ErrorDes.ERROR_INFO);
                stopRefresh();
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
        courserCouponAdapter = new CourserCouponAdapter(CashCouponActivity.this, classBeanList);
        lvCourseCoupon.setAdapter(courserCouponAdapter);
        setListViewHeightBasedOnChildren(lvCourseCoupon);

    }

    /**
     * 设置现金数和签到天数
     *
     * @param allBean
     */
    private void showDaysAndPrice(CashCouponBean.DataBean.AllBean allBean) {
        //如果数据为空，return
        if (null == allBean) {
            tvCashCoupons.setText(getString(R.string.RMB_symbol) + "0");
            tvSignDays.setText("您已累计签到0天，请继续努力哦~");
            return;
        }
        String rmb = allBean.getPrice();
        if (null != rmb) {
            tvCashCoupons.setText(getString(R.string.RMB_symbol) + allBean.getPrice());
        } else {
            tvCashCoupons.setText(getString(R.string.RMB_symbol) + "0");
        }

        String days = allBean.getDays();
        if (null != days) {
            tvSignDays.setText("您已累计签到" + days + "天，请继续努力哦~");
        } else {
            tvSignDays.setText("您已累计签到0天，请继续努力哦~");
        }

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
}
