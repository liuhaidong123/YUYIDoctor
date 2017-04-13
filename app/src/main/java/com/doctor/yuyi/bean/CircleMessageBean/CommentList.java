package com.doctor.yuyi.bean.CircleMessageBean;

/**
 * Created by liuhaidong on 2017/4/10.
 */

public class CommentList {
    private String createTimeString;

    private String updateTimeString;

    private String contentId;

    private String pid;

    private String avatar;

    private String content;

    private Integer likeNum;

    private String trueName;

    private String physicianId;

    private String commentType;

    private long id;

    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
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
    public void setContentId(String contentId){
        this.contentId = contentId;
    }
    public String getContentId(){
        return this.contentId;
    }
    public void setPid(String pid){
        this.pid = pid;
    }
    public String getPid(){
        return this.pid;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setLikeNum(Integer likeNum){
        this.likeNum = likeNum;
    }
    public Integer getLikeNum(){
        return this.likeNum;
    }
    public void setTrueName(String trueName){
        this.trueName = trueName;
    }
    public String getTrueName(){
        return this.trueName;
    }
    public void setPhysicianId(String physicianId){
        this.physicianId = physicianId;
    }
    public String getPhysicianId(){
        return this.physicianId;
    }
    public void setCommentType(String commentType){
        this.commentType = commentType;
    }
    public String getCommentType(){
        return this.commentType;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
