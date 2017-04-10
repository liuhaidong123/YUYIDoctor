package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/4/7.
 */

public class Bean_MySetting_ContactUs {

    /**
     * result : {"createTimeString":"","updateTimeString":"","tellNum":"0312-3850331","id":2,"tellText":"联系电话"}
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
         * tellNum : 0312-3850331
         * id : 2
         * tellText : 联系电话
         */

        private String createTimeString;
        private String updateTimeString;
        private String tellNum;
        private int id;
        private String tellText;

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

        public String getTellNum() {
            return tellNum;
        }

        public void setTellNum(String tellNum) {
            this.tellNum = tellNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTellText() {
            return tellText;
        }

        public void setTellText(String tellText) {
            this.tellText = tellText;
        }
    }
}
