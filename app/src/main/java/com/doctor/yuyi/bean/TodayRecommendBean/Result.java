package com.doctor.yuyi.bean.TodayRecommendBean;

/**
 * Created by liuhaidong on 2017/4/5.
 */

public class Result {
    private int commentNum;
    private long id;

    private String picture;

    private String content;

   private String title;;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

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
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}
