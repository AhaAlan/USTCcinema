package com.example.cinema.bean.vo;

import java.util.List;

//批量下架电影类
public class MovieBatchOffForm {
    //要下架的电影id列表
    private List<Integer> movieIdList;

    public List<Integer> getMovieIdList() {
        return movieIdList;
    }

    public void setMovieIdList(List<Integer> movieIdList) {
        this.movieIdList = movieIdList;
    }
}
