package com.example.cinema.controller.statistics;

import com.example.cinema.service.statistics.StatisticsService;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@RestController
public class StatisticsController {
    @Resource
    private StatisticsService statisticsService;

    @RequestMapping(value = "statistics/scheduleRate", method = RequestMethod.GET)
    public ResponseVO getScheduleRateByDate(@RequestParam(required = false) Date date){
        //此处date为非必填参数，若不填则默认为当天
        return statisticsService.getScheduleRateByDate(date);
    }

    @RequestMapping(value = "statistics/boxOffice/total", method = RequestMethod.GET)
    public ResponseVO getTotalBoxOffice(){
        return statisticsService.getTotalBoxOffice();
    }

    @RequestMapping(value = "statistics/audience/price", method = RequestMethod.GET)
    public ResponseVO getAudiencePrice(){
        return statisticsService.getAudiencePriceSevenDays();
    }

    @RequestMapping(value = "statistics/PlacingRate", method = RequestMethod.GET)
    public ResponseVO getMoviePlacingRateByDate(@RequestParam(required = false) Date date){
        return statisticsService.getMoviePlacingRateByDate(date);
    }

    @RequestMapping(value = "statistics/popular/movie", method = RequestMethod.GET)
    public ResponseVO getPopularMovies(@RequestParam int days, @RequestParam int movieNum){
        return statisticsService.getPopularMovies(days, movieNum);
    }










}
