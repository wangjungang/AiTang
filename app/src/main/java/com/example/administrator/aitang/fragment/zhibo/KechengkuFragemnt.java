package com.example.administrator.aitang.fragment.zhibo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.ClassListAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.bean.zhibo.MyClassBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.ui.zhibo.KechengXiangqingActivity;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class KechengkuFragemnt extends MyBaseFragment implements AdapterView.OnItemClickListener {


    @BindView(R.id.rl_kechengkuFrg_wukecheng)
    LinearLayout ll_wukecheng;
    @BindView(R.id.lsv_kechengku)
    ListView lsv;
    ClassListAdapter adapter;
    ArrayList<ClassBean.DataBean> been = new ArrayList<>();

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Bundle bundle = getArguments();//从activity传过来的Bundle
//        if (bundle != null) {
//            this.been = bundle.getParcelableArrayList("datas");
//        }
        return inflater.inflate(R.layout.fragment_kechengku_fragemnt, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void init() {
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);

        xRefreshView.startRefresh();
        lsv.setOnItemClickListener(this);
    }

    private void initData() {
        OkHttpUtils.get().url(MyConstant.CLASS_USERCLASSLIST).addParams("uid", Myapp.spUtil.getUid()).addParams("token", Myapp.spUtil.getToken())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                Log.d("TAG", "response=" + response);
                JSONObject jsonObject = new JSONObject(response);
                String code = jsonObject.getString("code");
                if (code.equals("201")) {//请求成功没有数据
                    ll_wukecheng.setVisibility(View.VISIBLE);
                    xRefreshView.setVisibility(View.GONE);
                } else if (code.equals("200")) {
                    been.clear();
                    MyClassBean bean = new Gson().fromJson(response, MyClassBean.class);
                    List<MyClassBean.DataBean> data1 = bean.getData();
                    for (int i = 0; i < data1.size(); i++) {
                        MyClassBean.DataBean.ClassBean data1_i = data1.get(i).getClassX();
                        ArrayList<MyClassBean.DataBean.TeacherBean> teachers = data1.get(i).getTeacher();
                        ArrayList<ClassBean.DataBean.TeacherBean> tea = new ArrayList<ClassBean.DataBean.TeacherBean>();
                        for (int j = 0; j < teachers.size(); j++) {
                            Log.d("TAG", "size=" + teachers.size() + "j=" + j);
                            MyClassBean.DataBean.TeacherBean teacher = teachers.get(j);
                            ClassBean.DataBean.TeacherBean db = new ClassBean.DataBean.TeacherBean(teacher.getTid(), teacher.getTname(), teacher.getTpic(), teacher.getTintro(), teacher.getTsimple(), teacher.getTscore(), teacher.getRoomid());
                            tea.add(db);
                        }

                        been.add(i, new ClassBean.DataBean(data1_i.getC_id(), data1_i.getCcid(), data1_i.getC_name(), data1_i.getC_price(), data1_i.getC_pay_num(), data1_i.getC_start_time(), data1_i.getC_end_time(), data1_i.getC_end_pay(), data1_i.getC_intro_img(), data1_i.getC_intro(), data1_i.getC_qq(), data1_i.getC_type(), data1_i.getTime(), 1, 1, tea));
                    }
                    adapter = new ClassListAdapter(getActivity(), been, true);
                    lsv.setAdapter(adapter);
                    stopRefresh();
                }
            }
        });


    }

    @Override
    public void addData() {
        initData();
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassBean.DataBean bean = (ClassBean.DataBean) adapter.getItem(i);
        Intent intent = new Intent(getActivity(), KechengXiangqingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", bean);
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }
}
