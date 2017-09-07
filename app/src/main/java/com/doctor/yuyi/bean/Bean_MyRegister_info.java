package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/4/11.
 */
//挂号详情
public class Bean_MyRegister_info {

    /**
     * result : {"clinicName":"呼吸内科门诊","departmentName":"内科","createTimeString":"2017-08-24 11:46:13","personalId":17743516301,"gender":1,"physicianTrueName":"孙思邈","homeuserId":216,"isAm":false,"visitTimeString":"2017-08-24 00:00:00","telephone":17743516301,"hospitalName":"涿州市中医医院","datenumberId":2,"trueName":"刘","physicianId":2,"id":36,"age":27}
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
         * clinicName : 呼吸内科门诊
         * departmentName : 内科
         * createTimeString : 2017-08-24 11:46:13
         * personalId : 17743516301
         * gender : 1
         * physicianTrueName : 孙思邈
         * homeuserId : 216
         * isAm : false
         * visitTimeString : 2017-08-24 00:00:00
         * telephone : 17743516301
         * hospitalName : 涿州市中医医院
         * datenumberId : 2
         * trueName : 刘
         * physicianId : 2
         * id : 36
         * age : 27
         */

        private String clinicName;
        private String departmentName;
        private String createTimeString;
        private long personalId;
        private String gender;
        private String physicianTrueName;
        private int homeuserId;
        private boolean isAm;
        private String visitTimeString;
        private String telephone;
        private String hospitalName;
        private int datenumberId;
        private String trueName;
        private int physicianId;
        private int id;
        private String age;

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
