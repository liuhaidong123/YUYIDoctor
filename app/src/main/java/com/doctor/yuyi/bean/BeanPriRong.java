package com.doctor.yuyi.bean;

/**
 * Created by liuhaidong on 2017/5/2.
 */

public class BeanPriRong {
    /**
     * code : 0
     * PermissionInfo : true
     * id : 52
     * TrueName : 岳不群
     * Avatar : /static/image/avatar.jpeg
     * token : U3bf3zTc3E84nUXfo5Wefim+/4mVRkjkKe63vV33TZeb169J8Giz/Daz4oyAtybl2BMxFCG2Sp2LUrYonRYzkJiuMHkYBWqN
     */

    private int code;
    private boolean PermissionInfo;
    private Long id;
    private String TrueName;
    private String Avatar;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isPermissionInfo() {
        return PermissionInfo;
    }

    public void setPermissionInfo(boolean PermissionInfo) {
        this.PermissionInfo = PermissionInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String TrueName) {
        this.TrueName = TrueName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
