package com.example.cinema.bean.vo;

import java.util.Date;

//每日客单价VO类
public class AudiencePriceVO {
    //当天日期
    private Date date;
    //客单价
    private Double price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
