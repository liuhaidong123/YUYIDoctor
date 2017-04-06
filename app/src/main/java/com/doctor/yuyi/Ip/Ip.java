package com.doctor.yuyi.Ip;

/**
 * Created by wanyu on 2017/4/5.
 */

public interface Ip {
    public final static String URL="http://192.168.1.55:8080/yuyi/physician/";

    //获取验证码接口http://192.168.1.55:8080/yuyi/physician/vcode.do?id=13717883006
    public final static String interface_SMSCode="vcode.do?";

    //登陆接口http://192.168.1.55:8080/yuyi/physician/login.do?id=13717883006&vcode=617307
    public final static String interface_Login="login.do?";

    //获取个人信息接口http://192.168.1.55:8080/yuyi/physician/get.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public final static String interface_UserInfo="get.do?";
}
