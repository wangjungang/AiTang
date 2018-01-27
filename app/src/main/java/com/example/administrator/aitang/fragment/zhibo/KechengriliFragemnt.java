package com.example.administrator.aitang.fragment.zhibo;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.RecyclerViewOnItemClickListener;
import com.example.administrator.aitang.adapter.zhibo.KechengkuAdatper;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.bean.zhibo.ClassBean1;
import com.example.administrator.aitang.bean.zhibo.KechengkuBean;
import com.example.administrator.aitang.bean.zhibo.MyClassBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.ui.zhibo.KechengXiangqingActivity;
import com.example.administrator.aitang.utils.CalendarUtil;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.google.gson.Gson;
import com.necer.ncalendar.calendar.NCalendar;
import com.necer.ncalendar.listener.OnCalendarChangedListener;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 点击日历的时候获取点击的年月日组成字符串，然后去kechengkubean1中查看有没有相同的，如果有就显示
 */
public class KechengriliFragemnt extends MyBaseFragment implements OnCalendarChangedListener, RecyclerViewOnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ncalendarrrr)
    NCalendar ncalendarrrr;
    @BindView(R.id.tv_nowTime)
    TextView tv_nowTime;
    Unbinder unbinder;
    KechengkuAdatper adatper;

    ArrayList<ClassBean.DataBean> been = new ArrayList<>();
    ArrayList<ClassBean1> been1;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        View rootView = inflater.inflate(R.layout.fragment_kechengrili_fragemnt, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adatper = new KechengkuAdatper(getActivity());
        adatper.setmOnItemClickListener(this);
        initData();
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
                } else if (code.equals("200")) {
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
                    been1 = new ArrayList<>();
                    //拿到been以后里面有全部数据，根据开始和结束时间取出所有时间，然后去标记时间
                    if (been != null && been.size() > 0) {
                        for (int i = 0; i < been.size(); i++) {
                            ClassBean.DataBean data = been.get(i);

                            //拿到的时间是2017-10-10的格式，需要分割
                            String startTime = DateUtil.toString(Long.parseLong(data.getC_start_time()), "yyyy-MM-dd");
                            String endTime = DateUtil.toString(Long.parseLong(data.getC_end_time()), "yyyy-MM-dd");
                            //算出来起始时间以后要遍历中间的时间
                            List<String> timeArray = CalendarUtil.queryData(startTime, endTime);
                            been1.add(new ClassBean1(timeArray, data));
                        }
                    }
                    Log.d("TAG", "been1.size=" + been1.size());
                    adatper.addAll(been, false);
                    recyclerView.setAdapter(adatper);
                    ncalendarrrr.setOnCalendarChangedListener(KechengriliFragemnt.this);


                    //被标记的日期
                    ncalendarrrr.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < been1.size(); i++) {
                                ClassBean1 kck = been1.get(i);
                                List<String> timeArray = kck.getTimeArray();
                                for (int j = 0; j < timeArray.size(); j++) {
                                    classs.add(timeArray.get(j));
                                }
                            }
                            ncalendarrrr.setPoint(new ArrayList<String>(classs));

                        }
                    });
                }
            }
        });


    }

    Set<String> classs = new HashSet<>();
    ArrayList<KechengkuBean.DataBean> datas2;
    //根据课程的id和课程的时间重新定义实体类
    Map<String, Object> map = new HashMap<>();
    List<ClassBean.DataBean> results = new ArrayList<>();

    //左右滑动日历和点击日历的时候都会执行这个方法
    @Override
    public void onCalendarChanged(DateTime dateTime) {
        results.clear();
        //获取到年月日以后跟获取的数据做对比，根据日期做查询
        String year = String.valueOf(dateTime.getYear());
        String month = String.valueOf(dateTime.getMonthOfYear());
        String day = String.valueOf(dateTime.getDayOfMonth());
        String time = year + "-" + (month.length() > 1 ? month : "0" + month) + "-" + (day.length() > 1 ? day : "0" + day);
        tv_nowTime.setText(time);
        //拿到点击的日期以后，去bean里查找对应的日期，日期是数组，有一个符合的就跳出这个数组
        adatper.clear();
        adatper.notifyDataSetChanged();
        for (int i = 0; i < been1.size(); i++) {
            ClassBean1 result = been1.get(i);
            List<String> timeArray = result.getTimeArray();
            a:
            for (int j = 0; j < timeArray.size(); j++) {
                String time_ = timeArray.get(j);
                Log.d("TAG", "time=" + time + "--aa=" + time_);
                if (time.equals(time_)) {
                    results.add(result.getDataBean());
                    break a;
                }
            }
        }
        if (results.size() > 0) {
            adatper.addAll(results, false);
        } else {
            Toast.makeText(getActivity(), "当天无课程", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, int i) {
        ClassBean.DataBean bean = (ClassBean.DataBean) adatper.getItem(i);
        Intent intent = new Intent(getActivity(), KechengXiangqingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", bean);
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }
}
