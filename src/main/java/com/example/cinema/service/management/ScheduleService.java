package com.example.cinema.service.management;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScheduleBatchDeleteForm;
import com.example.cinema.bean.vo.ScheduleForm;
import com.example.cinema.bean.vo.ScheduleViewForm;
import com.example.cinema.dao.po.ScheduleItem;

import java.util.Date;
import java.util.List;


public interface ScheduleService {
    //添加排片信息
    ResponseVO addSchedule(ScheduleForm scheduleForm);

    //查询包括从起始时间开始的7天排片计划
    ResponseVO searchScheduleSevenDays(int hallId, Date startDate);

    //设置排片对观众的可见的天数(全局设置,暂时只涉及天数)
    //若设置7天，且今天是04-11，则观众可见04-11到04-17的排片信息，其他均不可见*/
    ResponseVO setScheduleView(ScheduleViewForm scheduleViewForm);

    //批量删除排片信息
    ResponseVO deleteBatchOfSchedule(ScheduleBatchDeleteForm scheduleBatchDeleteForm);

    //修改排片信息
    ResponseVO updateSchedule(ScheduleForm scheduleForm);

    //根据id获取schedule
    ResponseVO getScheduleById(int id);

    //查询排片对观众的可见的天数
    ResponseVO getScheduleView();

    //观众看到的排片信息
    ResponseVO searchAudienceSchedule(int movieId);

    //查询所有涉及到movieIdList中电影的排片信息
    List<ScheduleItem> getScheduleByMovieIdList(List<Integer> movieIdList);

    //根据id查找排片信息
    ScheduleItem getScheduleItemById(int id);
}
