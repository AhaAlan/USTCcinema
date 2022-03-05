package com.example.cinema.dao.po;

import java.sql.Date;

/**
 * 喜爱统计类
 */
public class DateLike {
    //喜爱人数
    private int likeNum;
    //点击喜爱的时间
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
