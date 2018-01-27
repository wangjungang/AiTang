package com.example.administrator.aitang.fragment.lianxi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DaanAdapter;
import com.example.administrator.aitang.bean.lianxi.PiGaiDetailBean;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.utils.HtmlStrUtils;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PiGaiDetailFragment extends MyBaseFragment {

    int num;//当前题目在解析题的下标
    @BindView(R.id.tv_ksznlxAct_timucontent)
    HtmlTextView tvTimucontent;
    //    @BindView(R.id.ll_question)
//    LinearLayout llQuestiomn;
//    @BindView(R.id.tv_question)
//    TextView tvQuestion;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    //    @BindView(R.id.iv_answer_1)
//    ImageView ivAnswer1;
//    @BindView(R.id.iv_answer_2)
//    ImageView ivAnswer2;
//    @BindView(R.id.iv_answer_3)
//    ImageView ivAnswer3;
    @BindView(R.id.tv_pigai)
    TextView tvPigai;
    @BindView(R.id.ll_zhuguanti_content_view)
    LinearLayout llZhuguantiContentView;
    @BindView(R.id.sv_ksznlxAct)
    ScrollView svKsznlxAct;
    View v;

    private PiGaiDetailBean.DataBean data;
    private int index;//当前题目在总题数的下标
    private DaanAdapter adapter;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pigai_detail, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            data = (PiGaiDetailBean.DataBean) bundle.getSerializable("data");
            index = bundle.getInt("index");
        }
        return v;
    }

    @Override
    public void init() {
        if (data != null) {

            //题目
            List<List<String>> questionintro = data.getQuestionintro();

            List<String> questionintroStrList = new ArrayList<>(); //按照服务器返回的数据保存成list是因为要换行

            for (int i = 0; i < questionintro.size(); i++) {
                StringBuilder contentBuilder = new StringBuilder("");
                List<String> strList = questionintro.get(i);
                for (int k = 0; k < strList.size(); k++) {
                    String contStr = strList.get(k).replace("&nbsp;", "");
                    if (!StringUtils.isEmpty(contStr) && contStr.contains("http://")) {
                        String s = "<br/><img src='" + contStr + "'/><br/>";
                        contentBuilder.append(s);
                    } else {
                        contentBuilder.append(contStr);
                    }
                }
                questionintroStrList.add(contentBuilder.toString());
            }


            tvTimucontent.setHtmlFromString(HtmlStrUtils.getInstance().getHtmlString(questionintroStrList), false);


//            //如果标题不为空，添加标题
//            String title = data.getDes().replace("&nbsp;", "").replace("zxtc", "");
//            if (!StringUtils.isEmpty(title)) {
//                //添加为新的标题
//                String content = "(主观题)  " + title;
//                SpannableStringBuilder builder = new SpannableStringBuilder(content);
//                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tvQuestion.setText(builder);
//            } else {
//                llQuestiomn.setVisibility(View.GONE);
//            }


            String answer = data.getContent();
            tvAnswer.setText(answer);

            String des = data.getDes();
            tvPigai.setText(des);

        }
    }

}
