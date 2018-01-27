package com.example.administrator.aitang.adapter.account.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;

import com.example.administrator.aitang.bean.lianxi.IndexBean;
import com.example.administrator.aitang.bean.lianxi.IndexBean2;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;
import com.example.administrator.aitang.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class QuestionAdapter extends MyBaseAdapter<ShouYeBean.DataBean.QuetionsBean> {
    public Context context;
    public QuestionAdapter(Context context, List<ShouYeBean.DataBean.QuetionsBean> datasource) {
        super(context, datasource);
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.item_question,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ShouYeBean.DataBean.QuetionsBean bean = getItem(i);

//        Glide.with(context).load(bean.getImg()).into(holder.iv_img);

        GlideUtils.setAvatar(bean.getImg(), holder.iv_img);
        holder.tv_name.setText(bean.getQtname());

        return view;
    }
    class ViewHolder{
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_name)
        TextView tv_name;
    }
}
