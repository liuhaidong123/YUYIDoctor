package com.doctor.yuyi.bean;

/**
 * Created by wanyu on 2017/4/11.
 */
//挂号详情
public class Bean_MyRegister_info {

    /**
     * result : {"clinicName":"","departmentName":"","createTimeString":"2017-03-30 17:07:43","personalId":18511694068,"gender":null,"physicianTrueName":"","homeuserId":95,"isAm":true,"visitTimeString":"2017-03-16 00:00:00","telephone":null,"datenumberId":1,"trueName":"","physicianId":1,"id":24,"age":null}
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
         * clinicName :
         * departmentName :
         * createTimeString : 2017-03-30 17:07:43
         * personalId : 18511694068
         * gender : null
         * physicianTrueName :
         * homeuserId : 95
         * isAm : true
         * visitTimeString : 2017-03-16 00:00:00
         * telephone : null
         * datenumberId : 1
         * trueName :
         * physicianId : 1
         * id : 24
         * age : null
         */

        private String clinicName;
        private String departmentName;
        private String createTimeString;
        private long personalId;
        private Object gender;
        private String physicianTrueName;
        private int homeuserId;
        private boolean isAm;
        private String visitTimeString;
        private Object telephone;
        private int datenumberId;
        private String trueName;
        private int physicianId;
        private int id;
        private Object age;

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

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
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

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
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

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }
    }
}
