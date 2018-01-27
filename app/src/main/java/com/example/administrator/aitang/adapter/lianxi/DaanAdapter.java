package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.views.htmltextview.HtmlTextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class DaanAdapter extends MyBaseAdapter<String> {
    private int mCurrentIndex=999;//当前选中答案的下标，用来改变颜色

    public DaanAdapter(Context context, List<String> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_daan, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.img.setVisibility(View.VISIBLE);

        List<String> list = Arrays.asList(getItem(i).split("⑮"));
//        String answerStr = "";
        StringBuilder answerStrBuilder=new StringBuilder("");
        String imgUrl = "";
        for (int j = 0; j < list.size(); j++) {

            if (list.get(j).contains("http://")) {
                imgUrl = list.get(j);
            } else {
//                answerStr += list.get(j);
                answerStrBuilder.append(list.get(j));
            }
        }

        holder.tvInflateLsvitemDaanContent.setText(answerStrBuilder.toString());
        if (mCurrentIndex == i) {
            holder.tvInflateLsvitemDaanContent.setTextColor(context.getResources().getColor(R.color.color_FF9B19));
        } else {
            holder.tvInflateLsvitemDaanContent.setTextColor(context.getResources().getColor(R.color.color_444444));
        }

        if (!StringUtils.isEmpty(imgUrl)) {
            if (!imgUrl.equals(holder.img.getTag(R.id.img))) {
                holder.img.setTag(R.id.img, null);
                GlideUtils.loadImg(imgUrl, holder.img);
                holder.img.setTag(R.id.img, imgUrl);
            }

        } else {
            holder.img.setVisibility(View.GONE);
        }

        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.tv_inflate_lsvitem_daan_content)
        public HtmlTextView tvInflateLsvitemDaanContent;

        @BindView(R.id.img)
        public ImageView img;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 设置当前选中的，改变颜色为橙色
     *
     * @param currentIndex
     */
    public void setCurrentChecked(int currentIndex) {
        mCurrentIndex = currentIndex;
        notifyDataSetChanged();
    }
}
