package com.example.administrator.aitang.ui.zhibo;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.arialyy.annotations.DownloadGroup;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.download.DownloadGroupTask;
import com.arialyy.aria.core.inf.IEntity;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassDetailBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.FileUtils;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.example.administrator.aitang.utils.permission.PermissionRequest;
import com.example.administrator.aitang.views.DrawableCenterTextView;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.Call;

import static com.example.administrator.aitang.utils.FileUtils.sdcard;

public class ZhiboEndActivity extends MyBaseActivity {


    @BindView(R.id.img_jieshu_include)
    ImageView imgJieshu;
    @BindView(R.id.dctv_downloadNotes_include_zhibojieshu)
    DrawableCenterTextView dctvDownloadNotes;
    private String cdimg;//课程的图片
    private ClassDetailBean.DataBean data;
    private static final String IMGURL = "imgurl";
    private PermissionRequest permissionRequest;//权限请求工具类
    private String videoName;
    private String whiteName;
    private String whiteboardUrl;//白板下载地址
    private String videoUrl;//视屏下载地址

    private void pareIntent() {
        Bundle bundle = getIntent().getExtras();
        cdimg = bundle.getString(IMGURL);
        data = getIntent().getParcelableExtra("data");
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_zhibo_end);
    }

    @Override
    public void init() {
        pareIntent();
        setImmerBarcolor(imgJieshu);
        File file = new File(sdcard);
        if (!file.exists())//如f果目录不存在就创建目录
            file.mkdirs();
        Aria.download(this).register();//将对象注册到Aria (下载)
        GlideUtils.setAvatar(cdimg, imgJieshu, R.drawable.logo_img_qidongye, R.drawable.logo_img_qidongye, 0, 0);
        //下载讲义
        dctvDownloadNotes = (DrawableCenterTextView) findViewById(R.id.dctv_downloadNotes_include_zhibojieshu);
        dctvDownloadNotes.setOnClickListener(dcsvOnclick());
        dctvDownloadNotes.setVisibility(View.GONE);
        //先请求接口
        requestJiangYiData();
    }

    @NonNull
    private View.OnClickListener dcsvOnclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        };
    }

    /**
     * 请求权限，成功选择图片
     */
    private void requestPermissions() {
        if (null == permissionRequest) {
            permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
                @Override
                public void onSuccessful() {
                    //请求成功
                    downLoadJiangYi();
                }

                @Override
                public void onFailure() {
                    //失败，如果是拒绝默认提示，如果点击不再提示，下次提示跳转设置
                }
            });
        }
        //请求读写内存权限
        permissionRequest.requestGroup(Permission.STORAGE);

    }

    /**
     * 请求讲义的数据
     */
    private void requestJiangYiData() {
        String cdid = data.getCdid();
        OkHttpUtils.get().url(MyConstant.CLASS_GETLOOK)
                .addParams("uid", Myapp.spUtil.getUid())
                .addParams("token", Myapp.spUtil.getToken())
                .addParams("cdid", cdid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                Log.d("TAG", "下载相关log:" + response);
                JSONObject object = new JSONObject(response);
                String code = object.getString("code");
                if (code.equals("200")) {
                    dctvDownloadNotes.setVisibility(View.VISIBLE);

                    //数据请求成功的时候，返回的bean 里有两个参数：whiteboard、video
                    JSONObject jsonObject = object.getJSONObject("data");
                    whiteboardUrl = jsonObject.getString("whiteboard");//白板
                    videoUrl = jsonObject.getString("video");//视频
                    //初始化下载按钮状态
                    initDownloadBtnState();
                }
            }
        });
    }

    /**
     * 下载讲义
     */
    private void downLoadJiangYi() {
        String whiteboard = whiteboardUrl;//白板
        String video = videoUrl;//视频

        String downloadType = "";//下载类型，1为白板 2为视屏

        List<String> loadUrls = new ArrayList<String>();
        List<String> fileName = new ArrayList<String>();

        if (!TextUtils.isEmpty(video)) {
            loadUrls.add(video);
            videoName = getFileName(video);
            fileName.add(videoName);
        }
        if (!TextUtils.isEmpty(whiteboard)) {
            loadUrls.add(whiteboard);
            whiteName = getFileName(whiteboard);
            fileName.add(whiteName);
        }

        if (!StringUtils.isEmpty(whiteboard) && !StringUtils.isEmpty(video)) {
            downloadType = "1";
        } else {
            downloadType = "2";
        }
        if (!TextUtils.isEmpty(video)) {

            String[] urlSplitStr = video.split("\\?");
            Aria.download(this).load(loadUrls)
                    .setDownloadDirPath(sdcard)//路径
                    .setSubFileName(fileName)//文件名字
                    .setExtendField(urlSplitStr[0])//扩展字段，用于在缓存界面比较下载任务的数据与本地数据库是否为同一个的唯一标识
                    .start();

            //保存到数据库
            ContentValues contentValues = new ContentValues();

            contentValues.put("flagKey", urlSplitStr[0]);//在缓存界面本地数据与下载任务匹配的唯一标识
            contentValues.put("type", downloadType);//下载的类型 1：白板 2：视频
            contentValues.put("downLoadUrl", urlSplitStr[0]);//视屏下载的链接前缀，
            contentValues.put("className", data.getTname() + "-" + data.getCdintro());//课程名字
            contentValues.put("zhaiyao", data.getCdintro());//课程摘要
            String startTime = "";
            if (!StringUtils.isEmpty(data.getCdstart_time())) {
                startTime = DateUtil.toString(Long.parseLong(data.getCdstart_time()), "yyyy-MM-dd HH:mm");
            }
//            String endTime = DateUtil.toString(Long.parseLong(data.getCdend_time()), "yyyy-MM-dd HH:mm");
            contentValues.put("time", startTime);//时间
            contentValues.put("photo", data.getTeacher().getTpic());//图片网址
            contentValues.put("name", data.getTeacher().getTname());//老师的姓名
            contentValues.put("dis", data.getTeacher().getTsimple());//老师描述
            if (downloadType.equals("1")) {
                contentValues.put("whiteName", whiteName);//白板名字
            }
            contentValues.put("videoName", videoName);//视频名字
            contentValues.put("state", "1");//状态 1：下载完 2：未下载完 暂时没有用

            long id = Myapp.fileProvider.insert("filetab", contentValues);
            Log.d("TAG-xiazai", "id=" + id);
            if (id == -1)
                Log.d("TAG", "写入数据库失败");
            else
                Log.d("TAG", "写入数据库成功");

        }

    }

    /**
     * 初始化下载讲义按钮的状态
     */
    private void initDownloadBtnState() {

        //比较链接是否在本地中存在，文件是否还在
        //如果文件不存在,删除数据库和下载库中的记录,设置按钮可点击
        String[] urlSplitStr = videoUrl.split("\\?");
        String fileName = getFileName(videoUrl);
        if (!FileUtils.fileIsExists(fileName)) {
            //删除数据库
            Myapp.fileProvider.delete("filetab", "flagKey=?", new String[]{urlSplitStr[0]});

            //删除下载库记录
            List<DownloadGroupEntity> downList = Aria.download(this).getGroupTaskList();
            if (null != downList) {
                for (int j = 0; j < downList.size(); j++) {
                    List<String> urlList = downList.get(j).getUrls();
                    if (null != urlList && urlList.size() > 0) {
                        String[] urlStr = urlList.get(0).split("\\?");
                        if (urlStr[0].equals(urlSplitStr[0])) {
                            Aria.download(this).load(downList.get(j)).cancel(true);
                        }
                    }
                }
            }

            dctvDownloadNotes.setText("下载讲义");
            dctvDownloadNotes.setEnabled(true);
            Log.d("tag", "文件不存在，已删除记录");
        } else {
            //如果文件存在，查询状态是下载中还是已完成，显示按钮

            List<DownloadGroupEntity> downList = Aria.download(this).getGroupTaskList();
            if (null == downList) {
                //为空说明可能是卸载了app 数据库没有，但是文件还存在，此时重新下载

//                FileUtils.deleteFile(new File(fileName));
                dctvDownloadNotes.setText("下载讲义");
                dctvDownloadNotes.setEnabled(true);
            } else {
                for (int j = 0; j < downList.size(); j++) {
                    List<String> urlList = downList.get(j).getUrls();
                    if (null != urlList && urlList.size() > 0) {
                        String[] urlStr = urlList.get(0).split("\\?");
                        if (urlStr[0].equals(urlSplitStr[0])) {
                            int state = downList.get(j).getState();
                            if (state == IEntity.STATE_COMPLETE) {//下载完成，按钮显示为已下载，不可点击
                                dctvDownloadNotes.setText("已下载");
                                dctvDownloadNotes.setEnabled(false);
                            } else { //其他状态统一为 正在下载，不可点击
                                dctvDownloadNotes.setText("正在下载");
                                dctvDownloadNotes.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private String getFileName(String whiteboard) {
        String suffixes = "mp4|aac|gz|avi|mpeg|3gp|mp3|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(whiteboard);
        while (mc.find()) {
            return mc.group();//截取文件名后缀名
        }
        return null;
    }

    @DownloadGroup.onTaskStart
    void groupTaskStart(DownloadGroupTask task) {
        dctvDownloadNotes.setText("正在下载");
        dctvDownloadNotes.setEnabled(false);
    }

    @DownloadGroup.onTaskRunning()
    protected void running(DownloadGroupTask task) {

    }

    @DownloadGroup.onTaskComplete()
    void taskComplete(DownloadGroupTask task) {

        dctvDownloadNotes.setText("已下载");
        dctvDownloadNotes.setEnabled(false);

    }
}
