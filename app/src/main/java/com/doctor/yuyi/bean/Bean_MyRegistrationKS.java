package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/11.
 */

public class Bean_MyRegistrationKS {

    /**
     * result : [{"departmentName":"内科","createTimeString":"","updateTimeString":"","oid":1,"clinicList":[{"clinicName":"呼吸内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":1,"oid":1},{"clinicName":"消化内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":2,"oid":2},{"clinicName":"肾内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":3,"oid":0},{"clinicName":"心血管内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":4,"oid":0}],"hospitalId":1,"id":1,"open":true},{"departmentName":"妇产科","createTimeString":"","updateTimeString":"","oid":2,"clinicList":[{"clinicName":"免科门诊","createTimeString":"","updateTimeString":"","departmentId":2,"id":5,"oid":0},{"clinicName":"常规门诊","createTimeString":"","updateTimeString":"","departmentId":2,"id":6,"oid":0}],"hospitalId":1,"id":2,"open":false},{"departmentName":"五官科","createTimeString":"","updateTimeString":"","oid":3,"clinicList":[{"clinicName":"耳科门诊","createTimeString":"","updateTimeString":"","departmentId":3,"id":7,"oid":0},{"clinicName":"眼科门诊","createTimeString":"","updateTimeString":"","departmentId":3,"id":8,"oid":0}],"hospitalId":1,"id":3,"open":false},{"departmentName":"儿科","createTimeString":"","updateTimeString":"","oid":4,"clinicList":[{"clinicName":"小儿感冒发烧门诊","createTimeString":"","updateTimeString":"","departmentId":4,"id":9,"oid":0},{"clinicName":"疫苗门诊","createTimeString":"","updateTimeString":"","departmentId":4,"id":10,"oid":0}],"hospitalId":1,"id":4,"open":false},{"departmentName":"中医科","createTimeString":"","updateTimeString":"","oid":5,"clinicList":[],"hospitalId":1,"id":5,"open":false},{"departmentName":"骨科","createTimeString":"","updateTimeString":"","oid":6,"clinicList":[{"clinicName":"头部门诊","createTimeString":"","updateTimeString":"","departmentId":6,"id":11,"oid":0},{"clinicName":"胸部门诊","createTimeString":"","updateTimeString":"","departmentId":6,"id":12,"oid":0}],"hospitalId":1,"id":6,"open":false}]
     * code : 0
     */

    private String code;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * departmentName : 内科
         * createTimeString :
         * updateTimeString :
         * oid : 1
         * clinicList : [{"clinicName":"呼吸内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":1,"oid":1},{"clinicName":"消化内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":2,"oid":2},{"clinicName":"肾内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":3,"oid":0},{"clinicName":"心血管内科门诊","createTimeString":"","updateTimeString":"","departmentId":1,"id":4,"oid":0}]
         * hospitalId : 1
         * id : 1
         * open : true
         */

        private String departmentName;
        private String createTimeString;
        private String updateTimeString;
        private Long oid;
        private Long hospitalId;
        private Long id;
        private boolean open;
        private List<ClinicListBean> clinicList;

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

        public Long getOid() {
            return oid;
        }

        public void setOid(Long oid) {
            this.oid = oid;
        }

        public Long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(Long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public List<ClinicListBean> getClinicList() {
            return clinicList;
        }

        public void setClinicList(List<ClinicListBean> clinicList) {
            this.clinicList = clinicList;
        }

        public static class ClinicListBean {
            /**
             * clinicName : 呼吸内科门诊
             * createTimeString :
             * updateTimeString :
             * departmentId : 1
             * id : 1
             * oid : 1
             */

            private String clinicName;
            private String createTimeString;
            private String updateTimeString;
            private Long departmentId;
            private Long id;
            private Long oid;

            public String getClinicName() {
                return clinicName;
            }

            public void setClinicName(String clinicName) {
                this.clinicName = clinicName;
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

            public Long getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(Long departmentId) {
                this.departmentId = departmentId;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getOid() {
                return oid;
            }

            public void setOid(Long oid) {
                this.oid = oid;
            }
        }
    }
}
