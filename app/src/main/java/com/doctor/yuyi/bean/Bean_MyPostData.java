package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/7.
 */
//我的帖子
public class Bean_MyPostData {

    /**
     * total : 1
     * rows : [{"createTimeString":"2017-04-06 16:56:24","updateTimeString":"","isLike":true,"title":"34","shareNum":34,"content":"34","picture":"/static/image/avatar.jpeg","likeNum":23,"commentNum":12,"physicianId":34,"id":1}]
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
         * createTimeString : 2017-04-06 16:56:24
         * updateTimeString :
         * isLike : true
         * title : 34
         * shareNum : 34
         * content : 34
         * picture : /static/image/avatar.jpeg
         * likeNum : 23
         * commentNum : 12
         * physicianId : 34
         * id : 1
         */

        private String createTimeString;
        private String updateTimeString;
        private boolean isLike;
        private String title;
        private Long shareNum;
        private String content;
        private String picture;
        private Long likeNum;
        private Long commentNum;
        private Long physicianId;
        private Long id;

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

        public boolean isIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getShareNum() {
            return shareNum;
        }

        public void setShareNum(Long shareNum) {
            this.shareNum = shareNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
