package com.example.cinema.bean.vo;

import com.example.cinema.dao.po.DateLike;

import java.sql.Date;

public class DateLikeVO {
    //喜爱人数
    private int likeNum;

    //喜爱时间
    private Date likeTime;

    public DateLikeVO(DateLike dateLike){
        this.likeNum = dateLike.getLikeNum();
        this.likeTime = dateLike.getLikeTime();
    }

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
