package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.HistoryRealQuestiionBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class HistoryRealQuestionAdapter extends MyBaseAdapter<HistoryRealQuestiionBean.Data> {

    public HistoryRealQuestionAdapter(Context context, List<HistoryRealQuestiionBean.Data> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_history_real_question, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HistoryRealQuestiionBean.Data bean = getItem(i);
        String name = bean.getQcname();
        if(!StringUtils.isEmpty(name)){
            viewHolder.tv_name.setText(name);
        }
        String level = bean.getQdegree();
        if(!StringUtils.isEmpty(level)){
            viewHolder.tvLevel.setText("难度 "+level);
        }

        String isMake = bean.getIsmake();
        if(!StringUtils.isEmpty(isMake)){
            if(isMake.equals("0")){
                viewHolder.tvIsComplete.setText("未完成");
            }else{
                viewHolder.tvIsComplete.setText("已完成");
            }

        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_isComplete)
        TextView tvIsComplete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
