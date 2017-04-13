package com.doctor.yuyi.bean.PatientData;

/**
 * Created by liuhaidong on 2017/4/11.
 */

public class TemperatureList {
    private String createTimeString;

    private String updateTimeString;

    private long humeuserId;

    private long id;

    private float temperaturet;

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

    public float getTemperaturet() {
        return temperaturet;
    }

    public void setTemperaturet(float temperaturet) {
        this.temperaturet = temperaturet;
    }
}
