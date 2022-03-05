package com.example.cinema.dao.mapper.statistics;

import com.example.cinema.dao.po.AudiencePrice;
import com.example.cinema.dao.po.MovieScheduleTime;
import com.example.cinema.dao.po.MovieTotalBoxOffice;
import com.example.cinema.dao.po.PopularMovies;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StatisticsMapper {
    //确定date和 nextdate之间，每部电影的排片次数
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    //查询所有电影的总票房（包括已经下架的，降序排列）
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    //查询date和 nextdate之间，所有电影的票房（包括已经下架的，降序排列）
    List<PopularMovies> selectPopularMovies(@Param("date") Date date, @Param("nextDate") Date nextDate);

    //查询date和 nextdate之间，每个客户的购票金额
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);
}
