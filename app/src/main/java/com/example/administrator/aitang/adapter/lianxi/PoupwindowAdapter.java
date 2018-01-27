package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.IndexBean2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class PoupwindowAdapter extends MyBaseAdapter<IndexBean2.Data.Question.Child.Child2>{
    public Context mContxt;
    public PoupwindowAdapter(Context context, List<IndexBean2.Data.Question.Child.Child2> datasource) {
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
        IndexBean2.Data.Question.Child.Child2 bean = getItem(i);

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
