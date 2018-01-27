package com.example.administrator.aitang.adapter.wode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.wode.CashCouponBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourserCouponAdapter extends MyBaseAdapter<CashCouponBean.DataBean.ClassBean> {
    Context mContext;

    public CourserCouponAdapter(Context context, List<CashCouponBean.DataBean.ClassBean> datasource) {
        super(context, datasource);
        this.mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_cash_coupon, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CashCouponBean.DataBean.ClassBean bean = getItem(i);
        String price = bean.getUcprice();
        String count = bean.getNumber();


        //代金券价值
        if (!StringUtils.isEmpty(price)) {
            if (price.equals("100")) {//绿色
                holder.tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.color_08D2B2));
            } else if (price.equals("300")) {//橙色
                holder.tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.color_FF9B19));
            } else if (price.equals("500")) {//红色
                holder.tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.color_E51919));
            }
            holder.tvCouponPrice.setText(mContext.getString(R.string.RMB_symbol) + price);
            //代金券提示
            holder.tvCouponCountTip.setText(price + "元及" + price + "元以下课程任意券");
        }

        //代金券数量
        if (!StringUtils.isEmpty(count)) {
            holder.tvCouponCount.setText(count);
        }

        return view;
    }

    static class ViewHolder {

        @BindView(R.id.tv_coupon_price)
        TextView tvCouponPrice;
        @BindView(R.id.tv_coupon_count)
        TextView tvCouponCount;
        @BindView(R.id.tv_coupon_count_tip)
        TextView tvCouponCountTip;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
