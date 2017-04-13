package com.doctor.yuyi.bean.PatientData;

import java.util.List;

/**
 * Created by liuhaidong on 2017/4/11.
 */

public class Result {
    private String createTimeString;

    private Integer role;

    private String updateTimeString;

    private Integer gender;

    private String nickName;

    private long groupId;

    private long telephone;

    private String avatar;

    private String oid;

    private List<BloodpressureList> bloodpressureList;

    private String trueName;

    private String marital;

    private long id;

    private List<TemperatureList> temperatureList;

    private Integer age;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<BloodpressureList> getBloodpressureList() {
        return bloodpressureList;
    }

    public void setBloodpressureList(List<BloodpressureList> bloodpressureList) {
        this.bloodpressureList = bloodpressureList;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TemperatureList> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<TemperatureList> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
