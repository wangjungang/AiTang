package com.example.administrator.aitang.adapter.account;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.ExameTypeBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/22.
 */

public class ExameTypeAdapter extends MyBaseAdapter<ExameTypeBean.Data> {
    public Context mContext;
    private String checkIndex = "";

    public ExameTypeAdapter(Context context, List<ExameTypeBean.Data> datasource) {
        super(context, datasource);
        this.mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_exame_type, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ExameTypeBean.Data bean = getItem(i);
        String name = bean.getTestname();
        holder.tv_name.setText(name);
        holder.tv_name.setBackgroundResource(R.color.color_white);
        holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.color_646464));

        if (!StringUtils.isEmpty(checkIndex) && checkIndex.equals(String.valueOf(i))) {
            holder.tv_name.setBackgroundResource(R.color.color_08D2B2);
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.color_white));
        }

        return view;
    }

    public void changCheckItemn(String i) {
        checkIndex = i;
        notifyDataSetChanged();

    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.tv_name)
        TextView tv_name;

    }
}
