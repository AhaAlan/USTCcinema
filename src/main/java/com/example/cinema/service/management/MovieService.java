package com.example.cinema.service.management;

import com.example.cinema.bean.vo.MovieBatchOffForm;
import com.example.cinema.bean.vo.MovieForm;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.dao.po.Movie;


public interface MovieService {
    //上架电影
    ResponseVO addMovie(MovieForm addMovieForm);

    //根据id和userid搜索电影，可以知道这个用户是否点过想看这部电影
    ResponseVO searchOneMovieByIdAndUserId(int id, int userId);

    //搜索全部电影
    ResponseVO searchAllMovie();

    //搜索全部电影(不包括已经下架的)
    ResponseVO searchOtherMoviesExcludeOff();

    //根据关键字搜索电影
    ResponseVO getMovieByKeyword(String keyword);

    //批量下架电影
    ResponseVO pullOfBatchOfMovie(MovieBatchOffForm movieBatchOffForm);

    //更新电影信息
    ResponseVO updateMovie(MovieForm updateMovieForm);

    //根据类型搜索电影
    ResponseVO selectMovieByType(String type,int state);

    //获取电影的票房列表
    ResponseVO getBoxOfficeList();

    //获取电影的喜爱数据列表
    ResponseVO getLikeDataList();

    //根据id查找电影
    Movie getMovieById(int id);
}
