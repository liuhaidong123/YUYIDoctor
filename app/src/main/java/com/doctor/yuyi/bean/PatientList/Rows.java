package com.doctor.yuyi.bean.PatientList;

/**
 * Created by liuhaidong on 2017/4/12.
 */

public class Rows {
    private String avatar;

    private String trueName;

    private long id;

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setTrueName(String trueName){
        this.trueName = trueName;
    }
    public String getTrueName(){
        return this.trueName;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
