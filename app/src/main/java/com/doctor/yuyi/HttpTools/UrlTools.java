package com.doctor.yuyi.HttpTools;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {

//    public static final String BASE="http://192.168.1.55:8080/yuyi";
//    public static final String URL_FIRST_PAGE_SIX_DATA="/drugs/findList.do?start=0&limit=6&cid=11";//首页常用药品6条数据
//    public static final String URL_UPDATEA_FIRST="/article/findList.do?";//首页俩条数据，以及资讯全部数据（需传start=0&limit=2）
//    public static final String URL_UPDATEA_FIRST_MESSAGE="/article/getid.do?";////首页俩条数据，以及资讯全部数据的详情（传id=）
//
//    public static final String URL_FIRST_PAGE_TWO_DATA="/hospital/findList.do?";//咨询页面数据( 需传start=0&limit=2)
//    public static final String URL_FIRST_PAGE_TWO_DATA_MESSAGE="/hospital/get.do?";//咨询页面数据详情（传id）
//    public static final String URL_GET_VALIDATE_CODE="/personal/vcode.do";//获取验证码接口
//    public static final String URL_LOGIN="/personal/login.do";//登录接口
//    public static final String URL_USER_MESSAGE="/personal/get.do?";//我的页面用户信息接口（需要token）
//    public static  final  String URL_HOSPITAL_DEPARTMENT="/department/gethid.do?";//医院科室接口（需传hid=）
//    public static  final  String URL_USER_REGISTER="/datenumber/findList.do?";//用户挂号接口（需传门诊clinicId=3）
//
//    public static  final  String URL_AD="/article/findall.do";//首页广告轮播接口
//    public static final  String URL_AD_MEssage="/article/getid.do?";//首页轮播详情接口，需传id（id=3）
//    public static final  String URL_SEARCH_DRUG="/drugs/findvague.do?";//药品搜索接口 需传：vague=
//    public static final  String URL_SEARCH_HOSPITAL="/hospital/findvague.do?";//医院搜索接口 需传：vague=
//    public static final  String URL_USER_LIST="/homeuser/findList.do?";//获取用户列表（需传token=）
//    public static final  String URL_SUBMIT_TEM="/temperature/save.do";//提交体温数据post(token,humeuserId家庭用户编号,temperaturet体温);
//    public static final  String URL_SUBMIT_BLOOD="/bloodpressure/save.do";//提交体温数据post(token,humeuserId家庭用户编号,systolic收缩压（高压）,diastolic舒张压（低压）);
//
//    public static final  String URL_FIRST_PAGE_USER_DATA="/homeuser/findListHome.do?";//首页用户列表及默认用户的血压体温数据（token=）
//    public static final  String URL_CLICK_USER_HEAD_FIRST_PAGE="/homeuser/findOne.do?";//点击首页用户头像接口（token=6DD620E22A92AB0AED590DB66F84D064&humeuserId=10）
//    public static final  String URL_REGISTER="/register/save.do";//确定挂号接口（post方法：参数：token String，homeuserId Long家庭成员ID ；datenumberId Long对应可预约号数量表的编号，isAm Boolean 是否为上午）

    public static final String BASE="http://192.168.1.55:8080/yuyi";
    public static final  String URL_AD="/doctorlyinformation/getall.do";//广告数据
    public static final  String URL_AD_MESSAGE="/doctorlyinformation/get.do?";//广告,今日，最新，热门数据详情（需传id=,token）
    public static final  String URL_TODAY_RECOMMEND="/doctorlyinformation/getTodayAll.do?";//今日推荐（需传start，limit）
    public static final  String URL_NEW="/doctorlyinformation/findPage.do?";//最新（需传start，limit）
    public static final  String URL_HOT="/doctorlyinformation/find.do?";//热门（需传start，limit）
    public static final  String URL_COMMEND_LIST="/comment/getConmentAll.do?";//评论列表（需传：id=4&start=0&limit=6）

}

