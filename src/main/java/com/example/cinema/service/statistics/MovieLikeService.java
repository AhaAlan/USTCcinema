package com.example.cinema.service.statistics;

import com.example.cinema.bean.base.ResponseVO;


public interface MovieLikeService {
    //想看电影
    ResponseVO likeMovie(int userId, int movieId);

    //取消想看电影
    ResponseVO unLikeMovie(int userId,int movieId);

    //统计想看电影的人数
    ResponseVO getCountOfLikes(int movieId);

    //获得电影每日的想看人数
    ResponseVO getLikeNumsGroupByDate(int movieId);
}
