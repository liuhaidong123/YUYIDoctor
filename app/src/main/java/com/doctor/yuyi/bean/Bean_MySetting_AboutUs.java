package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/4/7.
 */

public class Bean_MySetting_AboutUs {

    /**
     * result : {"createTimeString":"","updateTimeString":"","title":"宇医1.0","version":"当前版本号：1.0（wanyu2007）","content":"宇医APP，希望通过网上医疗的形式能够解决用户的一些医疗的基本需求，包括：测量监控自己及家人的健康数据；足不出户解决购药问题；提前预约专家挂号问题；在家与医生面对面交流，解决一些简单的问诊等","picture":"/static/image/avatar.jpeg","id":1}
     * code : 0
     */

    private ResultBean result;
    private String code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * createTimeString :
         * updateTimeString :
         * title : 宇医1.0
         * version : 当前版本号：1.0（wanyu2007）
         * content : 宇医APP，希望通过网上医疗的形式能够解决用户的一些医疗的基本需求，包括：测量监控自己及家人的健康数据；足不出户解决购药问题；提前预约专家挂号问题；在家与医生面对面交流，解决一些简单的问诊等
         * picture : /static/image/avatar.jpeg
         * id : 1
         */

        private String createTimeString;
        private String updateTimeString;
        private String title;
        private String version;
        private String content;
        private String picture;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
