package com.doctor.yuyi.bean.PatientData;

/**
 * Created by liuhaidong on 2017/4/11.
 */

public class Root {
    private Result result;

    private String code;

    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
