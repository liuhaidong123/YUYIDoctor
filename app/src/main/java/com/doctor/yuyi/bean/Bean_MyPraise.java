package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/7.
 */
//我的点赞
public class Bean_MyPraise {

    /**
     * total : 4
     * rows : [{"summary":"摘要","createTimeString":"2017-04-07 18:10:03","updateTimeString":"","contentId":1,"likeType":1,"title":"标题","picture":"/static/image/avatar.jpeg","likeNum":0,"commentNum":0,"physicianId":34,"id":1},{"summary":"34","createTimeString":"2017-04-13 17:42:54","updateTimeString":"","contentId":2,"likeType":1,"title":"34","picture":"/static/image/avatar.jpeg","likeNum":0,"commentNum":0,"physicianId":34,"id":5},{"summary":"summary","createTimeString":"2017-04-07 10:07:29","updateTimeString":"","contentId":1,"likeType":2,"title":"title","picture":"/static/image/avatar.jpeg","likeNum":1,"commentNum":2,"physicianId":34,"id":6},{"summary":"summary","createTimeString":"2017-04-18 10:07:58","updateTimeString":"","contentId":2,"likeType":2,"title":"title","picture":"/static/image/avatar.jpeg","likeNum":2,"commentNum":4,"physicianId":34,"id":7}]
     * colmodel : []
     */

    private Long total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean {
        /**
         * summary : 摘要
         * createTimeString : 2017-04-07 18:10:03
         * updateTimeString :
         * contentId : 1
         * likeType : 1
         * title : 标题
         * picture : /static/image/avatar.jpeg
         * likeNum : 0
         * commentNum : 0
         * physicianId : 34
         * id : 1
         */

        private String summary;
        private String createTimeString;
        private String updateTimeString;
        private Long contentId;
        private Long likeType;
        private String title;
        private String picture;
        private Long likeNum;
        private Long commentNum;
        private Long physicianId;
        private Long id;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public Long getContentId() {
            return contentId;
        }

        public void setContentId(Long contentId) {
            this.contentId = contentId;
        }

        public Long getLikeType() {
            return likeType;
        }

        public void setLikeType(Long likeType) {
            this.likeType = likeType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Long getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Long likeNum) {
            this.likeNum = likeNum;
        }

        public Long getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Long commentNum) {
            this.commentNum = commentNum;
        }

        public Long getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(Long physicianId) {
            this.physicianId = physicianId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
