package com.doctor.yuyi.bean.CircleMessageBean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/4/10.
 */

public class Result {
    private List<CommentList> commentList;

    private String createTimeString;

    private String updateTimeString;

    private boolean isLike;

    private String avatar;

    private String title;

    private int shareNum;

    private String content;

    private String picture;

    private Integer likeNum;

    private Integer commentNum;

    private String trueName;

    private int physicianId;

    private long id;

    public void setCommentList(List<CommentList> commentList){
        this.commentList = commentList;
    }
    public List<CommentList> getCommentList(){
        return this.commentList;
    }
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
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setShareNum(int shareNum){
        this.shareNum = shareNum;
    }
    public int getShareNum(){
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
    public void setTrueName(String trueName){
        this.trueName = trueName;
    }
    public String getTrueName(){
        return this.trueName;
    }
    public void setPhysicianId(int physicianId){
        this.physicianId = physicianId;
    }
    public int getPhysicianId(){
        return this.physicianId;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
