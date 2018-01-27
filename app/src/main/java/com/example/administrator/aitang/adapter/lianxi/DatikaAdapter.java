package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class DatikaAdapter extends MyBaseAdapter<Integer> {

    public DatikaAdapter(Context context, List<Integer> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_gvitem_datika, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_tiNum.setText(String.valueOf(getItem(i)));
        if(i>98){
            holder.tv_tiNum.setTextSize(RudenessScreenHelper.pt2px(context,12));
        }
        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.tv_inflate_gvitem_tinum)
        public  TextView tv_tiNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
