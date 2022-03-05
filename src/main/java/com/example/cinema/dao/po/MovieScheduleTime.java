package com.example.cinema.dao.po;

/**
 * 电影排片类
 */
public class MovieScheduleTime {
    private Integer movieId;
    private String name;
    //排片次数
    private Integer time;

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
