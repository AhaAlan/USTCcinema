package com.example.cinema.serviceImpl.management;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.*;
import com.example.cinema.service.management.MovieService;
import com.example.cinema.service.management.ScheduleService;
import com.example.cinema.dao.mapper.management.MovieMapper;
import com.example.cinema.dao.mapper.statistics.MovieLikeMapper;
import com.example.cinema.dao.mapper.statistics.StatisticsMapper;
import com.example.cinema.dao.po.Movie;
import com.example.cinema.dao.po.MovieTotalBoxOffice;
import com.example.cinema.dao.po.ScheduleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class MovieServiceImpl implements MovieService {
    private static final String SCHEDULE_ERROR_MESSAGE = "有电影后续仍有排片或已有观众购票且未使用";
    private static final String NO_TYPE ="无此类型影片";

    @Resource
    private MovieMapper movieMapper;
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private MovieLikeMapper movieLikeMapper;

    @Override
    public ResponseVO addMovie(MovieForm addMovieForm) {
        try {
            movieMapper.insertOneMovie(addMovieForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchOneMovieByIdAndUserId(int id, int userId) {
        try {
            Movie movie = movieMapper.selectMovieByIdAndUserId(id, userId);
            if(movie != null){
                return ResponseVO.buildSuccess(new MovieVO(movie)); //返回的是Movie对应的VO类
            }else{
                return ResponseVO.buildSuccess(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO searchAllMovie() {
        try {
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectAllMovie()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchOtherMoviesExcludeOff() {
        try {
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectOtherMoviesExcludeOff()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMovieByKeyword(String keyword) {
        if (keyword==null||keyword.equals("")){
            return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectAllMovie()));
        }
        return ResponseVO.buildSuccess(movieList2MovieVOList(movieMapper.selectMovieByKeyword(keyword)));
    }


    @Override
    public ResponseVO pullOfBatchOfMovie(MovieBatchOffForm movieBatchOffForm) {
        try {
            List<Integer> movieIdList =  movieBatchOffForm.getMovieIdList();
            ResponseVO responseVO = preCheck(movieIdList);  //下架和修改电影前的前置检查
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            movieMapper.updateMovieStatusBatch(movieIdList);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateMovie(MovieForm updateMovieForm) {
        try {
            ResponseVO responseVO = preCheck(Arrays.asList(updateMovieForm.getId()));//Arrays.asList将xxx转化成List集合的方法。
            if(!responseVO.getSuccess()){
                return responseVO;  //前置检测不成功，直接返回
            }
            movieMapper.updateMovie(updateMovieForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //下架和修改电影的前置检查
    public ResponseVO preCheck(List<Integer> movieIdList){
        Date thisTime = new Date();
        List<ScheduleItem> scheduleItems = scheduleService.getScheduleByMovieIdList(movieIdList);
        // 检查是否有电影后续有排片及是否有观众购票未使用
        for(ScheduleItem s : scheduleItems){
            if(s.getEndTime().after(thisTime)){
                return ResponseVO.buildFailure(SCHEDULE_ERROR_MESSAGE);
            }
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public Movie getMovieById(int id) {
        try {
            return movieMapper.selectMovieById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO selectMovieByType(String type,int state) {
        try {
            List<Movie> movies;
            if(type.equals("全部类型")){
                movies=movieMapper.selectAllMovie();
            }
            else{
                movies=movieMapper.selectMovieByType(type);
            }

            if(movies==null){
                return ResponseVO.buildFailure(NO_TYPE);
            }else {
                if(state==0){   //state=0，上架状态
                    List<Movie> temp=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        if(movies.get(i).getStatus()==0){
                            temp.add(movies.get(i));
                        }
                    }

                    if(temp.size()>0){
                        movies.clear(); //添加该状态电影前，先清空列表
                        for(int i=0;i<temp.size();i++){
                            movies.add(temp.get(i));
                        }
                    }else {
                        movies.clear();// List.clear() 方法用于从列表中移除所有元素。该方法没有返回值。
                    }

                }else if(state==1){ //state=1，下架状态
                    List<Movie> temp=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        if(movies.get(i).getStatus()==1){
                            temp.add(movies.get(i));
                        }
                    }
                    if(temp.size()>0){
                        movies.clear();
                        for(int i=0;i<temp.size();i++){
                            movies.add(temp.get(i));
                        }
                    }else {
                        movies.clear();
                    }
                }

                if(movies.size()==0){
                    return ResponseVO.buildFailure(NO_TYPE);
                }else{
                    List<MovieVO> movieVOS=new ArrayList<>();
                    for(int i=0;i<movies.size();i++){
                        MovieVO movieVO=new MovieVO(movies.get(i));
                        movieVOS.add(movieVO);
                    }
                    return ResponseVO.buildSuccess(movieVOS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBoxOfficeList(){
        try {
            List<MovieTotalBoxOffice> movieTotalBoxOffices=statisticsMapper.selectMovieTotalBoxOffice();
            if(movieTotalBoxOffices!=null){
                return ResponseVO.buildSuccess(movieTotalBoxOffices);
            }
            else {
                return ResponseVO.buildFailure("暂无票房信息！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getLikeDataList(){
        try {
            List<Movie> movies=movieMapper.selectOtherMoviesExcludeOff();// //搜索全部电影(不包括已经下架的)
            List<ForwardMovieForm> forwardMovieFormList =new ArrayList<>();
            List<ForwardMovieForm> res =new ArrayList<>(); //存放前十个受欢迎的电影
            for(int i=0;i<movies.size();i++){
                //本质上这一步做的是，把List<Movie> ——> List<ForwardMovieForm>(实现PO到VO的转变)
                ForwardMovieForm forwardMovieForm =new ForwardMovieForm();
                forwardMovieForm.setMovieId(movies.get(i).getId());
                forwardMovieForm.setName(movies.get(i).getName());
                forwardMovieForm.setSum(movieLikeMapper.selectLikeNums(movies.get(i).getId()));
                forwardMovieFormList.add(forwardMovieForm);
            }
            for(int i=100;i>0;i--){ //这里只是模拟最多100个喜爱人数
                if(res.size()==10){  //统计受喜爱的电影前十位
                    break;
                }else{
                    for(int m = 0; m< forwardMovieFormList.size(); m++){
                        if(forwardMovieFormList.get(m).getSum()==i){
                            res.add(forwardMovieFormList.get(m));
                        }
                    }
                }
            }
            return ResponseVO.buildSuccess(res);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //类型转换：Movie转MovieVO
    private List<MovieVO> movieList2MovieVOList(List<Movie> movieList){
        List<MovieVO> movieVOList = new ArrayList<>();
        for(Movie movie : movieList){
            movieVOList.add(new MovieVO(movie));
        }
        return movieVOList;
    }

}
