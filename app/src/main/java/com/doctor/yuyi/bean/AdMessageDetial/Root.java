package com.doctor.yuyi.bean.AdMessageDetial;

import java.io.Serializable;

/**
 * Created by liuhaidong on 2017/4/5.
 */

public class Root implements Serializable{
    private boolean state;

    private String summary;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    private String smallTitle;

    private String createTimeString;

    private String updateTimeString;

    private String author;

    private int oid;

    private String title;

    private Integer shareNum;

    private String content;

    private String picture;

    private Integer likeNum;

    private Integer commentNum;

    private long id;

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getSmallTitle() {
        return this.smallTitle;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getCreateTimeString() {
        return this.createTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public String getUpdateTimeString() {
        return this.updateTimeString;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getOid() {
        return this.oid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getShareNum() {
        return this.shareNum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getLikeNum() {
        return this.likeNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCommentNum() {
        return this.commentNum;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
}
