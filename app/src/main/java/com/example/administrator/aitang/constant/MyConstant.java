package com.example.administrator.aitang.constant;

/**
 * Created by Administrator on 2017/8/10.
 */

public class MyConstant {
    /*************************App配置******************************/
    //app版本号
    public static final String VERSIONCODE = "1.2";
    //QQ SDK id
    public static final String QQAPPID = "1106415033";
    //WX SDK id
    public static final String WXAPPID = "wx6861c74177fde4f6";
    //WxAppSecret
    public static final String WXAPPSECRET = "07c9fdd3be5df5b91b2dd83110460fad";


    public static enum Position {
        LEFT, CENTER, RIGHT
    }

    //intent的参数，跳转到试题页面的标题--1.智能组卷 2.预测试题 3.专项智能练习 4.历年真题 5.模拟试题
    public static final String XITITYPEFLAG = "xitishowtitleflag";
    public static final String ZHINENGZUJUAN = "1";
    public static final String YUCESHITI = "2";
    public static final String ZHUANXAINGLIANXI = "3";
    public static final String LINIANZHENTI = "4";
    public static final String MONISHITI = "5";
    //intent的参数，专项智能练习跳转到试题页面，请求习题需要的id
    public static final String QTID = "qtid";
    //intent的参数，历年真题跳转到试题页面，请求习题需要的id
    public static final String QCID = "qcid";


    public static boolean DEBUG = false;
    //判断是否第一次登录
    public static final String FIRSTRUN = "true";
    //判断是否实名
    public static final String SHIMING = "isshiming";
    //当前是否有用户登录
    public static final String ISLOGIN = "islogin";
    //当前是否是第三方登录
    public static final String ISTHIRDLOGIN = "isthirdlogin";
    //用户登录token
    public static final String TOKEN = "token";
    //SessionId
    public static final String SESSIONID = "sessionId";
    //手机号
    public static final String PHONE = "phone";
    //昵称
    public static final String NICK = "nick";
    public static final String UID = "uid";
    //im用户
    public static final String IMACCOUNT = "imAccount";
    //imtoken
    public static final String IMTOKEN = "imToken";
    //im用户信息
    public static final String IMUSERINFO = "imUserInfo";

    //用户信息
    public static final String USERINFO = "userInfo";


    /************************************* 接口信息 *********************************************/

//    public static final String NET = "http://55.irapidtech.net/"; //测试地址
        public static final String NET = "http://app.aitangedu.com/"; //正式地址
    //获取验证码
    public static final String VERFICATION = NET + "app.php/tool/verification";
    //注册
    public static final String REGIST = NET + "app.php/login/register";
    public static final String EXAMETYPE = NET + "app.php/tool/test";
    //登录
    public static final String LOGIN = NET + "app.php/login/login";
    //主界面
    public static final String SHOUYEMESSAGE = NET + "app.php/index/index";
    //签到
    public static final String SIGN = NET + "app.php/user/deport";
    //用户消息
    public static final String USERMESSAGE = NET + "app.php/user/userMessage";

    //消息详情
    public static final String MESSAGEDETAIL = NET + "app.php/user/messageDetail";
    //忘记密码
    public static final String FORGETPWD = NET + "app.php/login/forget";

    //--------------------------------------------------------------直播---------------------------------------------------
    //直播课程列表
    public static final String CLASS_CLASSLIST = NET + "app.php/class/classList/";
    //直播课程列表==>课程表
    public static final String CLASS_LISTDETAIL = NET + "app.php/class/listDetail";
    //老师的授课历史
    public static final String CLASS_TEACHERDETAIL = NET + "app.php/Class/teacherDetail";
    //老师的评价
    public static final String CLASS_COMMENT = NET + "app.php/Class/comment";
    //课程库/课程日历
    public static final String CLASS_USERCLASSLIST = NET + "app.php/class/userClassList/";
    //课程缓存
    public static final String USER_USERVIDEO = NET + "app.php/user/userVideo";
    //视频播放接口
    public static final String CLASS_GETLOOK = NET + "app.php/class/getlook";
    //--------------------------------------------------------------练习---------------------------------------------------
    //数据报告
    public static final String DATEREPORT_REPORT = NET + "app.php/DateReport/report/uid/2";
    //练习周报
    public static final String WEEKDEPORT = NET + "app.php/DateReport/weekdeport/uid/2";
    //错题本、收藏
    public static final String COLLECTION = NET + "app.php/user/userquestion";
    //收藏详情
    public static final String USERQUESTIONDETAIL = NET + "app.php/user/userQuestionDetail";

    //历年真题
    public static final String REALQUESTION = NET + "app.php/tool/realQuestion";
    //历年真题详情接口
    public static final String REALQUESTIONDETAIL = NET + "app.php/tool/realQuestionDetail";
    //智能练习,预测试题,专项
    public static final String EXERCISES = NET + "app.php/questions/practice/";
    //申论
    public static final String SPECIALPRACTICE = NET + "app.php/questions/specialpractice/";
    //模拟试题
    public static final String TESTPRACTICE = NET + "app.php/Questions/testPractice";
    //上传图片
    public static final String UPLOADIMAGE = NET + "app.php/tool/uploadImage";
    //提交答案app.php/Questions/practiceing
    public static final String UPLOADANSWER = NET + "app.php/Questions/practiceing";
    //提交申论订单
    public static final String ORDERINSERT = NET + "app.php/order/orderInsert";
    //添加收藏
    public static final String ADDUSERCOLLECTION = NET + "app.php/user/userCollection";

    //--------------------------------------------------------------支付---------------------------------------------------
    //支付宝
    public static final String ALIPAY = NET + "app.php/AliPay/sendString";
    //微信
    public static final String WXPAY = NET + "app.php/Wxpay/orderUnified";
    //微信关闭订单
    public static final String WXPAYORDERCLOSE = NET + "app.php/Wxpay/orderclose";
    //通知后台订单完成
    public static final String SUCCESSORDER = NET + "app.php/order/successOrder";

    //--------------------------------------------------------------设置---------------------------------------------------
    //修改信息
    public static final String UPDATEINFO = NET + "app.php/user/update";
    //获取用户信息
    public static final String GETUSERINFO = NET + "app.php/user/information";
    //代金券
    public static final String GETMINECOUPON = NET + "app.php/user/mineCoupon/";
    //爱唐播报
    public static final String AITANGBROADCAST = NET + "app.php/tool/slide";
    //意见反馈
    public static final String QUESTIONFEEDBACK = NET + "app.php/user/proposal";
    //山东名师琅琊榜
    public static final String TEACHERRANK = NET + "app.php/tool/ranking";
    //网站配置-联系我们界面的信息
    public static final String CONFIG = NET + "app.php/user/config";
    //评论老师
    public static final String TEACHERCOMMENT = NET + "app.php/user/commit";
}
