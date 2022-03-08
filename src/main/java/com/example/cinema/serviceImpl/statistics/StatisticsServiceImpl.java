package com.example.cinema.serviceImpl.statistics;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.*;
import com.example.cinema.dao.po.*;
import com.example.cinema.service.statistics.StatisticsService;
import com.example.cinema.dao.mapper.management.HallMapper;
import com.example.cinema.dao.mapper.management.MovieMapper;
import com.example.cinema.dao.mapper.management.ScheduleMapper;
import com.example.cinema.dao.mapper.sales.TicketMapper;
import com.example.cinema.dao.mapper.statistics.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private HallMapper hallMapper;
    @Resource
    private MovieMapper movieMapper;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private TicketMapper ticketMapper;

    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));
            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);//六天前的日期，也就是本次统计的开始日期
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
            List<PlacingRateVO> placingRateVOList=new ArrayList<>();
            List<Hall> hall= hallMapper.selectAllHall();
            double n=hall.size();
            double m=0;
            for(int i=0;i<hall.size();i++){
                int r=hall.get(i).getRow();
                int c=hall.get(i).getColumn();
                m=m+r*c;
            }
            if(date==null){
                date=new Date();
                date.getTime();
            }
            Date startDate = getNumDayAfterDate(date, -6);
            for(int i = -1; i < 6; i++){
                Date firstday=getNumDayAfterDate(startDate, i);
                Date secondday=getNumDayAfterDate(startDate,i+1);
                double totalmovietime=0;
                List<MovieScheduleTime> movieScheduleTimes=statisticsMapper.selectMovieScheduleTimes(firstday,secondday);
                for(int a=0;a<movieScheduleTimes.size();a++){
                    totalmovietime=totalmovietime+movieScheduleTimes.get(a).getTime();
                }
                List<Ticket> tickets=ticketMapper.selectTicketByDate(firstday,secondday);
                double totalviewer=tickets.size();
                double result;
                if(totalmovietime==0 || m==0 || n==0 ||totalviewer==0){
                    result=0;
                }
                else{
                    result=(((totalviewer/totalmovietime)/m)/n);
                }
                PlacingRateVO placingRateVO=new PlacingRateVO();
                placingRateVO.setPlacingrate(result);
                placingRateVO.setdate(secondday);
                placingRateVOList.add(placingRateVO);
            }
            return  ResponseVO.buildSuccess(placingRateVOList);

        } catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        try{
            Date current=new Date();
            current.getTime();
            Calendar cld = Calendar.getInstance();
            cld.setTime(current);
            cld.add(Calendar.DATE, -(days-1));
            Date otherday = cld.getTime();
            List<PopularMovies> popularMoviesList=statisticsMapper.selectPopularMovies(otherday,current);
            List<PopularMoviesVO> popularMoviesVOList=new ArrayList<>();
            for (int i =0;i<5;i++){
                PopularMoviesVO popularMoviesVO=new PopularMoviesVO();
                popularMoviesVO.setId(popularMoviesList.get(i).getMovieId());
                popularMoviesVO.setName(popularMoviesList.get(i).getName());
                popularMoviesVO.setBoxOffice(popularMoviesList.get(i).getBoxOffice());
                popularMoviesVOList.add(popularMoviesVO);
            }
            return ResponseVO.buildSuccess(popularMoviesVOList);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("获取受欢迎电影失败");
        }
    }


    //获得num天后的日期
    private Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }


    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }
}
