package com.example.cinema.bean.vo;

import java.util.Date;
import java.util.List;

//排片信息列表类
public class ScheduleListVO {

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
