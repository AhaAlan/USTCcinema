package com.example.cinema.service.statistics;

import com.example.cinema.bean.base.ResponseVO;

import java.util.Date;

public interface StatisticsService {
    //获取某日各影片排片率统计数据
    ResponseVO getScheduleRateByDate(Date date);

    //获取所有电影的累计票房(降序排序，且包含已下架的电影)
    ResponseVO getTotalBoxOffice();

    //返回过去7天内每天客单价,（某天的客单价=当天观众购票所花金额/购票人次数)
    ResponseVO getAudiencePriceSevenDays();

    //获取所有电影某天的上座率
    //上座率参考公式：假设某影城设有n 个电影厅、m 个座位数，相对上座率=观众人次÷放映场次÷m÷n×100%
    ResponseVO getMoviePlacingRateByDate(Date date);

    //获取最近days天内，最受欢迎的movieNum个电影(最近days内票房越高的电影越受欢迎)
    ResponseVO getPopularMovies(int days, int movieNum);
}
