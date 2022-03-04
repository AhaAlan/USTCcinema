package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;
import java.util.Date;
import java.util.List;

public class ScheduleVO {
    private Date date;
    private List<ScheduleItemVO> scheduleItemList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ScheduleItemVO> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItemVO> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }
}
