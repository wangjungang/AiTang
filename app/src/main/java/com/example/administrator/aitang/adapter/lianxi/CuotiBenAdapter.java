package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.CuotiBenBean;
import com.example.administrator.aitang.bean.lianxi.HistoryRealQuestiionBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class CuotiBenAdapter extends MyBaseAdapter<CuotiBenBean.Data>{
    public CuotiBenAdapter(Context context, List<CuotiBenBean.Data> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view ==null){
           view = inflater.inflate(R.layout.item_history_real_question,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CuotiBenBean.Data bean = getItem(i);
        String name = bean.getQtname();
        viewHolder.tv_name.setText(name);
        return view;
    }
static class ViewHolder{
    @BindView(R.id.tv_name)
    TextView tv_name;
    ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
}
