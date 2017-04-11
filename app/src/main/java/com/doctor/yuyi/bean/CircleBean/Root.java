package com.doctor.yuyi.bean.CircleBean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/4/10.
 */

public class Root {
    private int total;

    private List<Rows> rows;

    private List<String> colmodel;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setRows(List<Rows> rows){
        this.rows = rows;
    }
    public List<Rows> getRows(){
        return this.rows;
    }
    public void setColmodel(List<String> colmodel){
        this.colmodel = colmodel;
    }
    public List<String> getColmodel(){
        return this.colmodel;
    }
}
