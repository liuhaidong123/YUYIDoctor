package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/3/27.
 */
//测试数据
public class Bean_Test_My_message {
    private String imaPth;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImaPth() {
        return imaPth;
    }

    public void setImaPth(String imaPth) {
        this.imaPth = imaPth;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String title;
    private String msg;
    private String time;
    public Bean_Test_My_message(String imaPth,String title,String msg,String time){
        this.imaPth=imaPth;
        this.msg=msg;
        this.time=time;
        this.title=title;
    }
}
