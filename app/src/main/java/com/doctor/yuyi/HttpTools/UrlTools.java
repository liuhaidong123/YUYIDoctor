package com.doctor.yuyi.HttpTools;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {
    //public static final String BASE="http://192.168.1.168:8082/yuyi";
    public static final String BASE = "http://59.110.169.148:8080";
    public static final String URL_AD = "/doctorlyinformation/getall.do";//广告数据
    public static final String URL_AD_MESSAGE = "/doctorlyinformation/get.do?";//广告,今日，最新，热门数据详情（需传id=,token）
    public static final String URL_TODAY_RECOMMEND = "/doctorlyinformation/getTodayAll.do?";//今日推荐（需传start，limit）
    public static final String URL_NEW = "/doctorlyinformation/findPage.do?";//最新（需传start，limit）
    public static final String URL_HOT = "/doctorlyinformation/find.do?";//热门（需传start，limit）
    public static final String URL_COMMEND_LIST = "/comment/getConmentAll.do?";//评论列表（需传：id=4&start=0&limit=6）
    public static final String URL_COMMEND = "/comment/AddConment.do?";//评论(需telephone=18782931355&content_id=1&Content=haha)
    public static final String URL_PRAISE = "/likes/UpdateLikeNum.do?";//点赞接口（需：id=&token=）


    public static final String URL_CIRCLE_HOT = "/academicpaper/findhot.do?";//学术圈热门（start=0&limit=6,token=）
    public static final String URL_CIRCLE_SELECT = "/academicpaper/Selected.do?";//学术圈精选（start=0&limit=6token=）
    public static final String URL_CIRCLE_NEW = "/academicpaper/findtime.do?";//学术圈最新（start=0&limit=6token=）
    public static final String URL_NEW_SELECT_HOT_MESSAGE = "/academicpaper/academicpaperComment.do?";//token=start=0&limit=2&id=1热门，精选，最新详情

    public static final String URL_CIRCLE_PRAISE = "/likes/LikeNum.do?";//id=1&token=820F140709A478E3358AB5DA911C91E6 学术圈点赞接口

    public static final String URL_PATIENT_DATA = "/homeuser/findOnePh.do?";//token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&humeuserId=1患者详情中的患者数据
    public static final String URL_PATIENT_ELE = "/homeuser/findMyUserList.do?";//token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&humeuserId=1患者详情中的电子病历

    public static final String URL_CIRCLE_COMMEND = "/comment/AddConment2.do?";//telephone=18782931355&content_id=1&Content=haha学术圈详情评论
    public static final String URL_CIRCLE_COMMEND_PRAISE = "/likes/CommentLike.do?";//id=1&token=CEDA9F4E7D5FEC556E1BB035FA18E54E 学术圈详情评论点赞
    public static final String URL_MY_PATIENT = "/homeuser/findMyUserList.do?";//我的患者接口token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public static final String URL_SHARE_CARD = "/share/AcademicpaperShare.do?";//id=1&token=CEDA9F4E7D5FEC556E1BB035FA18E54E&shareType=1  帖子详情分享

    public static final String URL_SHARE_INFORMATION = "/share/UpdateShar.do?";//id=1&shareType=1&token=0365866B04DA5283B29330178F625C64  资讯详情分享

    //获取动态验证码
    public static final String URL_GET_DYNAMIC_NUM = "/personal/imgcode.do?";//参数ts=1515636702185当前毫秒数

    public static final String Url_CheckVersion="/aboutUs/getph.do";//检测版本号
}
