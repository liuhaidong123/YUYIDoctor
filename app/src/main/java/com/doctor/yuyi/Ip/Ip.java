package com.doctor.yuyi.Ip;

/**
 * Created by wanyu on 2017/4/5.
 */

public interface Ip {
    public final static String URL="http://192.168.1.55:8080/yuyi/";

    //获取验证码接口http://192.168.1.55:8080/yuyi/physician/vcode.do?id=13717883006
    public final static String interface_SMSCode="physician/vcode.do?";

    //登陆接口http://192.168.1.55:8080/yuyi/physician/login.do?id=13717883006&vcode=617307
    public final static String interface_Login="physician/login.do?";

    //获取个人信息接口http://192.168.1.55:8080/yuyi/physician/get.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public final static String interface_UserInfo="physician/get.do?";

    //获取我的帖子接口http://192.168.1.55:8080/yuyi/academicpaper/findmylist.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public final static String interface_MyPostData="academicpaper/findmylist.do?";

    //获取我的点赞接口http://192.168.1.55:8080/yuyi/likes/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public final static String interface_MyPraise="likes/findPage.do?";

    //删除我的点赞接口http://192.168.1.55:8080/yuyi/likes/delete.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&ids=10
    public final static String interface_DeleteMyPraise="likes/delete.do?";

    //意见反馈的接口http://192.168.1.55:8080/yuyi/feedback/saveforph.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&content=10&contact=123
    public final static String interface_MySetting_FeadUs="feedback/saveforph.do?";

    //联系我们http://192.168.1.55:8080/yuyi/contactUs/getph.do
    public final static String interface_ContactUs="contactUs/getph.do";

    //关于我们的接口http://192.168.1.55:8080/yuyi/aboutUs/getph.do
    public final static String interface_AboutUs="aboutUs/getph.do";
}
