package com.doctor.yuyi.bean.CommendListBean;

/**
 * Created by liuhaidong on 2017/4/6.
 */

public class Result {
    private String createTimeString;

    private String updateTimeString;

    private int contentId;

    private int pid;

    private String avatar;

    private String content;

    private String likeNum;

    private String trueName;

    private int physicianId;

    private int commentType;

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
    public void setContentId(int contentId){
        this.contentId = contentId;
    }
    public int getContentId(){
        return this.contentId;
    }
    public void setPid(int pid){
        this.pid = pid;
    }
    public int getPid(){
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
    public void setLikeNum(String likeNum){
        this.likeNum = likeNum;
    }
    public String getLikeNum(){
        return this.likeNum;
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
    public void setCommentType(int commentType){
        this.commentType = commentType;
    }
    public int getCommentType(){
        return this.commentType;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
