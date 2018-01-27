package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.IndexBean2;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class ShitiTypeContextAdapter  extends MyBaseAdapter<ShouYeBean.DataBean.QuetionsBean.ChildBean.Child2Bean>{
    public Context mContxt;
    public ShitiTypeContextAdapter(Context context, List<ShouYeBean.DataBean.QuetionsBean.ChildBean.Child2Bean> datasource) {
        super(context, datasource);
        this.mContxt = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.item_shiti_type_context,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ShouYeBean.DataBean.QuetionsBean.ChildBean.Child2Bean bean = getItem(i);

        Drawable drawable = mContxt.getResources().getDrawable(R.drawable.sanjitixing_icon_shouye);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        viewHolder.tv_name.setText(bean.getQtname());
        viewHolder.tv_name.setCompoundDrawables(drawable,null,null,null);
        return view;
    }

    static  class ViewHolder{
        @BindView(R.id.tv_name)
        TextView tv_name;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
