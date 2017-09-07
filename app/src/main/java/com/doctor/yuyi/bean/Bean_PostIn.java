package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/4/12.
 */
//发帖
public class Bean_PostIn {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : 帖子保存成功！
     * code : 0
     */
    String message;
    private String result;
    private String code;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
