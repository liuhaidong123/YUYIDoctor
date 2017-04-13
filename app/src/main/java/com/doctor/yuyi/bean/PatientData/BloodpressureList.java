package com.doctor.yuyi.bean.PatientData;

/**
 * Created by liuhaidong on 2017/4/11.
 */

public class BloodpressureList {
    private String createTimeString;

    private Integer diastolic;

    private Integer systolic;

    private String updateTimeString;

    private long humeuserId;

    private long id;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public long getHumeuserId() {
        return humeuserId;
    }

    public void setHumeuserId(long humeuserId) {
        this.humeuserId = humeuserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
