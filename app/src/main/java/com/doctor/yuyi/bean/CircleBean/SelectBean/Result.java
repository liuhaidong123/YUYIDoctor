package com.doctor.yuyi.bean.CircleBean.SelectBean;

/**
 * Created by liuhaidong on 2017/4/10.
 */

public class Result {
    private String createTimeString;

    private String updateTimeString;

    private boolean isLike;

    private String title;

    private Integer shareNum;

    private String content;

    private String picture;

    private Integer likeNum;

    private Integer commentNum;

    private Integer physicianId;

    private long id;

    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setIsLike(boolean isLike){
        this.isLike = isLike;
    }
    public boolean getIsLike(){
        return this.isLike;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setShareNum(Integer shareNum){
        this.shareNum = shareNum;
    }
    public Integer getShareNum(){
        return this.shareNum;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return this.picture;
    }
    public void setLikeNum(Integer likeNum){
        this.likeNum = likeNum;
    }
    public Integer getLikeNum(){
        return this.likeNum;
    }
    public void setCommentNum(Integer commentNum){
        this.commentNum = commentNum;
    }
    public Integer getCommentNum(){
        return this.commentNum;
    }
    public void setPhysicianId(Integer physicianId){
        this.physicianId = physicianId;
    }
    public Integer getPhysicianId(){
        return this.physicianId;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
