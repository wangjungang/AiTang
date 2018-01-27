package com.example.administrator.aitang.ui.lianxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DatikaAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.TianXieDaAnBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.KSZNLXFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.adapterData;
import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.tianXieDaAnList;

public class DaTiKa1Activity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_header_right)
    ImageView iv_right;
    @BindView(R.id.tv_datiAct_title)
    TextView tv_type;
    @BindView(R.id.gv_datiAct)
    MyGridView gv;
    @BindView(R.id.btn_datiAct_tijiao)
    Button btn_tijiao;
    List<Integer> datas;
    DatikaAdapter adapter;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    private int sum;//题目的总数量
    private long uptimes_start;//试题开始的时间
    private String xitiType;//习题的类型

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_da_ti_ka);
    }

    @Override
    public void init() {
        Myapp.xiTiActivities.add(this);
        setImmerBarcolor(llHeader);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("答题卡");
        //请求焦点，防止界面抖动
        tv_type.setFocusableInTouchMode(true);
        tv_type.setFocusable(true);

        xitiType = KSZNLXActivity.xitiType;
        if (StringUtils.isEmpty(xitiType)) {
            xitiType = "";
        }
        if (xitiType.equals(MyConstant.ZHINENGZUJUAN)) {
            tv_type.setText("智能组卷");
        } else if (xitiType.equals(MyConstant.YUCESHITI)) {
            tv_type.setText("预测试题");
        } else if (xitiType.equals(MyConstant.ZHUANXAINGLIANXI)) {
            tv_type.setText("专项智能练习");
        } else if (xitiType.equals(MyConstant.LINIANZHENTI)) {
            tv_type.setText("历年真题");
        } else if (xitiType.equals(MyConstant.MONISHITI)) {
            tv_type.setText("模拟试题");
        }

        Bundle bundle = getIntent().getExtras();
        sum = bundle.getInt("sum", 0);
        uptimes_start = bundle.getLong("uptimes_start");
        datas = new ArrayList<>();
        adapter = new DatikaAdapter(this, datas) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = super.getView(i, view, viewGroup);
                DatikaAdapter.ViewHolder holder = (ViewHolder) v.getTag();

                TianXieDaAnBean tianXieDaAnBean = KSZNLXActivity.tianXieDaAnList.get(i);
                String type = tianXieDaAnBean.getType();
                List<Integer> answerList = tianXieDaAnBean.getAnswerList();
                if (!StringUtils.isEmpty(type) && type.equals("0")) {//选择题
                    if (null == answerList || answerList.size() == 0) {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);

                    } else {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.yizuo_image_datika);
                    }
                } else if (!StringUtils.isEmpty(type) && type.equals("1")) {//简答题
                    if (StringUtils.isEmpty(tianXieDaAnBean.getContent())) {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);

                    } else {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.yizuo_image_datika);
                    }
                }

                return v;
            }
        };
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
        for (int i = 0; i < sum; i++) {
            adapter.add((i + 1));
        }
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right, R.id.btn_datiAct_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.btn_datiAct_tijiao://提交
                //调用接口提交，成功后跳转解析
                uploadTiJiaoData();

                break;
        }
    }

    /**
     * 上传要提交的信息
     */
    private void uploadTiJiaoData() {
        showProgressDialog();
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        String practiceType = "1";
        if (xitiType.equals(MyConstant.ZHINENGZUJUAN)) {
            practiceType = "1";
        } else if (xitiType.equals(MyConstant.YUCESHITI)) {
            practiceType = "2";
        } else if (xitiType.equals(MyConstant.ZHUANXAINGLIANXI)) {
            practiceType = "3";
        } else if (xitiType.equals(MyConstant.LINIANZHENTI)) {
            practiceType = "4";
        } else if (xitiType.equals(MyConstant.MONISHITI)) {
            practiceType = "4";
        }

        StringBuilder upquestionBuilder = new StringBuilder("");
        StringBuilder upyesBuilder = new StringBuilder("");
        StringBuilder upnoBuilder = new StringBuilder("");

        for (int i = 0; i < adapterData.size(); i++) {

            if (i == adapterData.size() - 1) {
                upquestionBuilder.append(tianXieDaAnList.get(i).gettId());
                upyesBuilder.append(adapterData.get(i).getQsuccess());
            } else {
                upquestionBuilder.append(tianXieDaAnList.get(i).gettId() + ",");
                upyesBuilder.append(adapterData.get(i).getQsuccess() + ",");
            }
        }

        for (int i = 0; i < tianXieDaAnList.size(); i++) {
            if (i == tianXieDaAnList.size() - 1) {
                if (null != tianXieDaAnList.get(i).getAnswerList() && tianXieDaAnList.get(i).getAnswerList().size() > 0) {
                    upnoBuilder.append(getPos(tianXieDaAnList.get(i).getAnswerList().get(0)));
                }

            } else {
                if (null != tianXieDaAnList.get(i).getAnswerList() && tianXieDaAnList.get(i).getAnswerList().size() > 0) {
                    upnoBuilder.append(getPos(tianXieDaAnList.get(i).getAnswerList().get(0)) + ",");
                } else {
                    upnoBuilder.append(",");
                }

            }
        }
        String upquestion = upquestionBuilder.toString();
        String upyes = upyesBuilder.toString();
        String upno = upnoBuilder.toString();
        String uptimes = String.valueOf((SystemClock.elapsedRealtime() - uptimes_start)/1000);

        //主观题相关
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < tianXieDaAnList.size(); i++) {
            TianXieDaAnBean tianXieDaAnBean = tianXieDaAnList.get(i);
            Map map = null;
            if (tianXieDaAnBean.getType().equals("1")) {//如果是主观题
                map = new HashMap();
                map.put("qid", tianXieDaAnBean.gettId());
                map.put("content", tianXieDaAnBean.getContent());
                map.put("img", tianXieDaAnBean.getImgPathList());
                list.add(map);
            }

        }

        String uplistStr = new Gson().toJson(list);

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("practiceType", practiceType);//1 智能组卷 2预测试题 3专项智能练习 4模拟题 4历年真题
        paramsMap.put("upquestion", upquestion);//题目id(,)
        paramsMap.put("upyes", upyes);//正确答案(,)
        paramsMap.put("upno", upno);//错误答案(,)
        paramsMap.put("uptimes", uptimes);//所用时间
        paramsMap.put("uplist", uplistStr);//主观题id、答案和图片

        HttpManager.getInstance().post(MyConstant.UPLOADANSWER, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                hideProgressDialog();
                //提交成功，跳转解析
                startTiJiaoIntent();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                hideProgressDialog();
                toast(result.getDesc());
            }
        });
    }

    /**
     * 根据选项获取下标
     *
     * @param
     */
    private String getPos(int index) {
        String[] s = {"A", "B", "C", "D", "E", "F", "G"};
        int[] indexStrs = {0, 1, 2, 3, 4, 5, 6};

        for (int i = 0; i < indexStrs.length; i++) {

            if (index == indexStrs[i]) {
                return s[i];
            }
        }
        return "";
    }

    /**
     * 跳转解析的Intent
     */
    private void startTiJiaoIntent() {
        Intent intent = new Intent(this, LXBGActivity.class);
        intent.putExtra("sum", sum);
        intent.putExtra("flag", "2");
        intent.putExtra("time", DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm"));
        startActivityForResult(intent, KSZNLXFragment.REQUEST_CODE_JIEXI);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        KSZNLXActivity.vp_KsznlxAct.setCurrentItem(i);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KSZNLXFragment.REQUEST_CODE_JIEXI && resultCode == KSZNLXFragment.RESULT_CODE_JIEXI) {
            Myapp.clearXiTiActivities();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.xiTiActivities.remove(this);
    }
}
