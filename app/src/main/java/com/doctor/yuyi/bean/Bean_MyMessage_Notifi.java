package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/10.
 */

public class Bean_MyMessage_Notifi {

    /**
     * total : 2
     * rows : [{"createTimeString":"2017-04-06 13:54:49","msgType":1,"updateTimeString":"","targetId":34,"isRead":true,"avatar":"1","title":"1","content":"1","physicianId":1,"referId":1,"id":1,"operation":1},{"createTimeString":"2017-04-06 13:55:27","msgType":2,"updateTimeString":"","targetId":34,"isRead":false,"avatar":"2","title":"2","content":"2","physicianId":2,"referId":2,"id":2,"operation":2}]
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
         * createTimeString : 2017-04-06 13:54:49
         * msgType : 1
         * updateTimeString :
         * targetId : 34
         * isRead : true
         * avatar : 1
         * title : 1
         * content : 1
         * physicianId : 1
         * referId : 1
         * id : 1
         * operation : 1
         */

        private String createTimeString;
        private int msgType;
        private String updateTimeString;
        private Long targetId;
        private boolean isRead;
        private String avatar;
        private String title;
        private String content;
        private Long physicianId;
        private Long referId;
        private Long id;
        private Long operation;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public Long getTargetId() {
            return targetId;
        }

        public void setTargetId(Long targetId) {
            this.targetId = targetId;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(Long physicianId) {
            this.physicianId = physicianId;
        }

        public Long getReferId() {
            return referId;
        }

        public void setReferId(Long referId) {
            this.referId = referId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOperation() {
            return operation;
        }

        public void setOperation(Long operation) {
            this.operation = operation;
        }
    }
}
