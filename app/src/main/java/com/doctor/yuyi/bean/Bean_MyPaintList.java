package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/10.
 */
//我的患者liebao
public class Bean_MyPaintList {

    /**
     * total : 35
     * rows : [{"avatar":"/static/image/avatar.jpeg","trueName":"我自己","id":94},{"avatar":"/static/image/2017/3/31/1490950727552.jpg","trueName":"18511694068","id":95},{"avatar":"/static/image/2017/4/5/1491359630715.jpg","trueName":"刘海东","id":96},{"avatar":"/static/image/2017/3/31/1490949414737.jpg","trueName":"","id":97},{"avatar":"/static/image/2017/3/24/1490346917455.jpg","trueName":"bbb","id":100}]
     * colmodel : []
     */

    private Long total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
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
         * avatar : /static/image/avatar.jpeg
         * trueName : 我自己
         * id : 94
         */

        private String avatar;
        private String trueName;
        private Long id;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
