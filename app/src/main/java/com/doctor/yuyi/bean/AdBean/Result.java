package com.doctor.yuyi.bean.AdBean;

/**
 * Created by liuhaidong on 2017/4/5.
 */

public class Result {
    private  long id;

    private String picture;

    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return this.picture;
    }
}
