package com.example.cinema.dao.mapper.promotion;

import com.example.cinema.dao.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {
    //新建优惠活动
    int insertActivity(Activity activity);
    //针对每个电影，新建一个活动
    int insertActivityAndMovie(@Param("activityId") int activityId,@Param("movieId") List<Integer> movieId);
    //根据活动id查询优惠活动
    Activity selectById(int id);
    //查询所有优惠活动
    List<Activity> selectActivities();
    //根据电影id查询优惠活动
    List<Activity> selectActivitiesByMovie(int movieId);
    //查询没有进行优惠活动的电影列表
    List<Activity> selectActivitiesWithoutMovie();






}
