package com.example.cinema.po;


import java.sql.Date;

public class DateLike {
    /**
     * 喜爱人数
     */
    private int likeNum;

    /**
     * 喜爱时间
     */
    private Date likeTime;

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}
