package com.doctor.yuyi.bean;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MyEntity {
    private String title;
    private String name;
    private int id;

    public MyEntity(String title, String name, int id) {
        this.title = title;
        this.name = name;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
