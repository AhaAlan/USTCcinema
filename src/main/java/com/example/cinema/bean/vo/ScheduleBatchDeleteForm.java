package com.example.cinema.bean.vo;

import java.util.List;

//批量删除排片信息的类
public class ScheduleBatchDeleteForm {
    /**
     * 要删除的排片信息id列表
     */
    private List<Integer> scheduleIdList;

    public List<Integer> getScheduleIdList() {
        return scheduleIdList;
    }

    public void setScheduleIdList(List<Integer> scheduleIdList) {
        this.scheduleIdList = scheduleIdList;
    }
}
