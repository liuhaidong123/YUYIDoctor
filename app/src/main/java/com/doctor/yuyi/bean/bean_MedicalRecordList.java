package com.doctor.yuyi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/8/31.
 */

public class bean_MedicalRecordList {

    /**
     * result : [{"departmentName":"内科","createTimeString":"2017-08-31 16:21:26","updateTimeString":"","gender":null,"departmentId":null,"medicalrecord":"电子病历","mid":19,"avatar":"","hospitalName":"涿州市中医医院","picture":"","trueName":"","marital":null,"physicianName":"","hospitalId":null,"id":null,"age":null}]
     * code : 0
     * message : msg
     */

    private String code;
    private String message;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * departmentName : 内科
         * createTimeString : 2017-08-31 16:21:26
         * updateTimeString :
         * gender : null
         * departmentId : null
         * medicalrecord : 电子病历
         * mid : 19
         * avatar :
         * hospitalName : 涿州市中医医院
         * picture :
         * trueName :
         * marital : null
         * physicianName :
         * hospitalId : null
         * id : null
         * age : null
         */

        private String departmentName;
        private String createTimeString;
        private String updateTimeString;
        private String gender;
        private String departmentId;
        private String medicalrecord;
        private int mid;
        private String avatar;
        private String hospitalName;
        private String picture;
        private String trueName;
        private String marital;
        private String physicianName;
        private String hospitalId;
        private String id;
        private String age;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
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

        public Object getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getMedicalrecord() {
            return medicalrecord;
        }

        public void setMedicalrecord(String medicalrecord) {
            this.medicalrecord = medicalrecord;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Object getMarital() {
            return marital;
        }

        public void setMarital(String marital) {
            this.marital = marital;
        }

        public String getPhysicianName() {
            return physicianName;
        }

        public void setPhysicianName(String physicianName) {
            this.physicianName = physicianName;
        }

        public Object getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public Object getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
