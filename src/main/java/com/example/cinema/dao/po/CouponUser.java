package com.example.cinema.dao.po;

/**
 * 优惠券使用人员
 */
public class CouponUser {
    //优惠卷的id
    private  int id;
    //用户的id
    private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
    }
}
