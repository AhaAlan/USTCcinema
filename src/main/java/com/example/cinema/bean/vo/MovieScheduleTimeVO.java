package com.example.cinema.bean.vo;

import com.example.cinema.dao.po.MovieScheduleTime;

public class MovieScheduleTimeVO {
    private Integer movieId;
    /**
     * 排片次数
     */
    private Integer time;
    private String name;

    public MovieScheduleTimeVO(MovieScheduleTime movieScheduleTime){
        this.movieId = movieScheduleTime.getMovieId();
        this.time = movieScheduleTime.getTime();
        this.name = movieScheduleTime.getName();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
