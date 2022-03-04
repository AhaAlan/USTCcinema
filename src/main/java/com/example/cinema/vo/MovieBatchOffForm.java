package com.example.cinema.vo;

import java.util.List;

public class MovieBatchOffForm {
    /**
     * 要下架的电影id列表
     */
    private List<Integer> movieIdList;

    public List<Integer> getMovieIdList() {
        return movieIdList;
    }

    public void setMovieIdList(List<Integer> movieIdList) {
        this.movieIdList = movieIdList;
    }
}
