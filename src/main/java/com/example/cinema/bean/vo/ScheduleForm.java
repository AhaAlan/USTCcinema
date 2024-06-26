package com.example.cinema.bean.vo;

import java.util.Date;

//增加排片的所需信息类（不包括hallName、username等无用信息）
public class ScheduleForm {
    //id
    private Integer id;
    //影厅id
    private Integer hallId;
    //电影id
    private Integer movieId;
    //电影开始放映的时间
    private Date startTime;
    //电影结束放映的时间
    private Date endTime;
    //电影票价
    private Double fare;

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
