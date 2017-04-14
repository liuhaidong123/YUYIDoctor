package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/12.
 */
//搜索患者
public class BeanSearch {

    /**
     * total : 36
     * rows : [{"avatar":"/static/image/avatar.jpeg","trueName":"张试试","id":1},{"avatar":"/static/image/avatar.jpeg","trueName":"我自己","id":94}]
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
         * avatar : /static/image/avatar.jpeg
         * trueName : 张试试
         * id : 1
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
