package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.lianxi.KSZNLXActivity;
import com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class ShitiTypeAdapter extends MyBaseAdapter<ShouYeBean.DataBean.QuetionsBean.ChildBean> {

    List<ShouYeBean.DataBean.QuetionsBean.ChildBean> childList = new ArrayList<>();
    public Context mContext;

    public ShitiTypeAdapter(Context context, List<ShouYeBean.DataBean.QuetionsBean.ChildBean> datasource) {
        super(context, datasource);
        this.mContext = context;
        this.childList = datasource;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder_ShitiType holer;
        if (view == null) {
            view = inflater.inflate(R.layout.item_shiti_type, null);
            holer = new ViewHolder_ShitiType(view);
            view.setTag(holer);
        } else {
            holer = (ViewHolder_ShitiType) view.getTag();
        }
        final ShouYeBean.DataBean.QuetionsBean.ChildBean bean = childList.get(i);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.yijitixing_icon_shouye);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holer.tv_name.setText(bean.getQtname());
        holer.tv_name.setCompoundDrawables(drawable, null, null, null);

        final List<ShouYeBean.DataBean.QuetionsBean.ChildBean.Child2Bean> child2list = bean.getChild();
        if (null != child2list && child2list.size() != 0) {
            holer.gridview.setVisibility(View.VISIBLE);

            Log.i(">>>>>", child2list.toString());
            ShitiTypeContextAdapter adapter = new ShitiTypeContextAdapter(mContext, child2list);
            holer.gridview.setAdapter(adapter);
        } else {
            holer.gridview.setVisibility(View.GONE);

        }
        //请求习题需要的qtid
        final String qtid = bean.getQtid();
        final String name = bean.getQtname();

        final String isCharge = bean.getIscharge();//1是申论，收费  2为普通

        //添加点击事件，跳转至快速智能练习界面
        holer.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(isCharge) && isCharge.equals("1")) {//申论
                    Intent intent = new Intent(mContext, ShenLunActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString(MyConstant.QTID, qtid);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, KSZNLXActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.ZHUANXAINGLIANXI);
                    bundle.putString("name", name);
                    bundle.putString(MyConstant.QTID, qtid);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });


        holer.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                Bundle bundle = null;

                if (!StringUtils.isEmpty(isCharge) && isCharge.equals("1")) {//申论
                    intent = new Intent(mContext, ShenLunActivity.class);
                    bundle = new Bundle();
                } else {
                    intent = new Intent(mContext, KSZNLXActivity.class);
                    bundle = new Bundle();
                    bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.ZHUANXAINGLIANXI);

                }

                String qtid = child2list.get(i).getQtid();
                String name = child2list.get(i).getQtname();
                bundle.putString(MyConstant.QTID, qtid);
                bundle.putString("name", name);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    static class ViewHolder_ShitiType {
        ViewHolder_ShitiType(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.gridview)
        MyGridView gridview;
    }


}
