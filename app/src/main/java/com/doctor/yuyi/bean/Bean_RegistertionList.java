package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/11.
 */
//挂号列表
public class Bean_RegistertionList {

    /**
     * total : 5
     * rows : [{"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-03-30 17:07:43","personalId":18511694068,"gender":1,"physicianTrueName":"李时珍","homeuserId":95,"isAm":true,"visitTimeString":"2017-03-16 00:00:00","telephone":18511694068,"datenumberId":1,"trueName":"18511694068","physicianId":1,"id":24,"age":26},{"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-03-30 17:09:30","personalId":18511694068,"gender":null,"physicianTrueName":"李时珍","homeuserId":98,"isAm":true,"visitTimeString":"2017-03-16 00:00:00","telephone":null,"datenumberId":1,"trueName":"","physicianId":1,"id":25,"age":null},{"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-03-31 09:13:47","personalId":18301264693,"gender":0,"physicianTrueName":"李时珍","homeuserId":117,"isAm":true,"visitTimeString":"2017-03-31 00:00:00","telephone":null,"datenumberId":1,"trueName":"绿","physicianId":1,"id":27,"age":55},{"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-03-31 10:41:20","personalId":18301264693,"gender":1,"physicianTrueName":"孙思邈","homeuserId":116,"isAm":true,"visitTimeString":"2017-03-31 00:00:00","telephone":18301264693,"datenumberId":2,"trueName":"刘大东","physicianId":2,"id":28,"age":26},{"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-03-31 10:41:52","personalId":18301264693,"gender":1,"physicianTrueName":"李时珍","homeuserId":116,"isAm":true,"visitTimeString":"2017-03-31 00:00:00","telephone":18301264693,"datenumberId":1,"trueName":"刘大东","physicianId":1,"id":29,"age":26}]
     * colmodel : []
     */

    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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
         * clinicName : 呼吸内科门诊
         * departmentName : 内科
         * createTimeString : 2017-03-30 17:07:43
         * personalId : 18511694068
         * gender : 1
         * physicianTrueName : 李时珍
         * homeuserId : 95
         * isAm : true
         * visitTimeString : 2017-03-16 00:00:00
         * telephone : 18511694068
         * datenumberId : 1
         * trueName : 18511694068
         * physicianId : 1
         * id : 24
         * age : 26
         */

        private String clinicName;
        private String departmentName;
        private String createTimeString;
        private long personalId;
        private int gender;
        private String physicianTrueName;
        private int homeuserId;
        private boolean isAm;
        private String visitTimeString;
        private long telephone;
        private int datenumberId;
        private String trueName;
        private int physicianId;
        private int id;
        private int age;

        public String getClinicName() {
            return clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

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

        public long getPersonalId() {
            return personalId;
        }

        public void setPersonalId(long personalId) {
            this.personalId = personalId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPhysicianTrueName() {
            return physicianTrueName;
        }

        public void setPhysicianTrueName(String physicianTrueName) {
            this.physicianTrueName = physicianTrueName;
        }

        public int getHomeuserId() {
            return homeuserId;
        }

        public void setHomeuserId(int homeuserId) {
            this.homeuserId = homeuserId;
        }

        public boolean isIsAm() {
            return isAm;
        }

        public void setIsAm(boolean isAm) {
            this.isAm = isAm;
        }

        public String getVisitTimeString() {
            return visitTimeString;
        }

        public void setVisitTimeString(String visitTimeString) {
            this.visitTimeString = visitTimeString;
        }

        public long getTelephone() {
            return telephone;
        }

        public void setTelephone(long telephone) {
            this.telephone = telephone;
        }

        public int getDatenumberId() {
            return datenumberId;
        }

        public void setDatenumberId(int datenumberId) {
            this.datenumberId = datenumberId;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(int physicianId) {
            this.physicianId = physicianId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
