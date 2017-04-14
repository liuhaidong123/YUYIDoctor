package com.doctor.yuyi.Ip;

/**
 * Created by wanyu on 2017/4/5.
 */

public interface Ip {
//    http://59.110.169.148:8080/
    public final static String URL="http://192.168.1.55:8080/yuyi/";
    public final static String ImgPth="http://192.168.1.55:8080/yuyi";


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

    //我的数据患者列表http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public final static String interface_MyPaintList="homeuser/findAllUserList.do?";


    //我的消息列表接口http://192.168.1.55:8080/yuyi/messagePhysician/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public final static String interface_MyMessageList="messagePhysician/findPage.do?";

    //消息已读标记://192.168.1.55:8080/yuyi/messagePhysicianLog/save.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&messageId=1
    public final static String interface_MyMessageRead="messagePhysicianLog/save.do?";

    //获取挂号中所有科室的列表:http://192.168.1.55:8080/yuyi/department/getallph.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public final static String interface_MyRegisterKS="department/getallph.do?";

    //获取挂号中所有科室的列表:http://192.168.1.55:8080/yuyi/register/findList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&departmentId=1&clinicId=1
    public final static String interface_MyRegisterGH="register/findList.do?";


    //获取挂号中所有科室的列表http://192.168.1.55:8080/yuyi/register/get.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&id=1
    public final static String interface_MyRegisterGH_Msg="register/get.do?";


    //获取挂号中所有科室的列表我的帖子点赞接口http://192.168.1.55:8080/yuyi/likes/LikeNum.do?id=1&token=820F140709A478E3358AB5DA911C91E6
    public final static String interface_MyPostDataPraise="likes/LikeNum.do?";

    //发表帖子的接口
    public final  static String interface_PostIn="academicpaper/AddAcademicpaper.do?";

    //获取搜索患者的结果http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&trueName=1
    public final  static String interface_Searchpaint="homeuser/findAllUserList.do?";
}
