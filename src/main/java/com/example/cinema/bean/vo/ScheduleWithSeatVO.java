package com.example.cinema.bean.vo;

import com.example.cinema.dao.po.ScheduleItem;

//带有座位信息的排片类
//用来记录该场次的被锁座位
public class ScheduleWithSeatVO {
    //排片
    private ScheduleItem scheduleItem;

    //座位
    private int[][] seats;

    public ScheduleItem getScheduleItem() {
        return scheduleItem;
    }

    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }
}
