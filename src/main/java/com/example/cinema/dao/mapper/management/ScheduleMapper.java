package com.example.cinema.dao.mapper.management;

import com.example.cinema.bean.vo.ScheduleForm;
import com.example.cinema.dao.po.ScheduleItem;
import com.example.cinema.bean.vo.ScheduleViewForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

@Mapper
public interface ScheduleMapper {
    //插入一条排片信息
    int insertOneSchedule(ScheduleForm scheduleForm);

    //查询从startDate到endDate的某hall的排片信息（范围内）
    List<ScheduleItem> selectSchedule(@Param("hallId") int hallId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //查询data的某hall的排片信息（现在这个时刻）
    List<ScheduleItem> selectScheduleHall(@Param("hallId") int hallId, @Param("nowDate") Date nowDate);

    //查询起止时间是否有冲突(不包括与自身的冲突)
    List<ScheduleItem> selectScheduleConflictByHallIdAndTime(@Param("hallId") int hallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("id") int id);

    //插入一条排片，包括观众可见天数
    int insertOneView(ScheduleViewForm scheduleViewForm);

    //修改观众可见天数
    int updateOneView(ScheduleViewForm scheduleViewForm);

    //查询view的记录数，以此判断后续操作是插入还是修改
    int selectViewCount();

    //批量删除排片信息
    int deleteScheduleBatch(List<Integer> scheduleIdList);

    //批量查询排片信息
    List<ScheduleItem> selectScheduleBatch(List<Integer> scheduleIdList);

    //查询排片限制信息
    int selectView();

    //根据id修改排片信息
    int updateScheduleById(ScheduleForm scheduleForm);

    //根据排片id查找排片信息
    ScheduleItem selectScheduleById(@Param("id") int id);

    //查询所有涉及到 movieIdList中电影的排片信息
    List<ScheduleItem> selectScheduleByMovieIdList(List<Integer> movieIdList);

    //根据movieId，查询该电影的所有排片信息
    List<ScheduleItem> selectScheduleByMovieId(@Param("movieId") int movieId);

}
