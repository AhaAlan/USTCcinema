package com.example.cinema.dao.po;

import com.example.cinema.bean.vo.ActivityVO;
import com.example.cinema.bean.vo.MovieVO;
import com.example.cinema.dao.po.Coupon;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠活动类
 */
public class Activity {
    //活动id
    private int id;
    //活动名
    private String name;
    //活动描述
    private String description;
    //活动开始时间
    private Timestamp startTime;
    //活动截止时间
    private Timestamp endTime;
    //优惠电影列表
    private List<Movie> movieList;
    //优惠券
    private Coupon coupon;

    //获取优惠活动对应的VO
    public ActivityVO getVO() {
        ActivityVO vo = new ActivityVO();
        vo.setId(id);
        vo.setCoupon(coupon);
        vo.setDescription(description);
        vo.setEndTime(endTime);
        vo.setStartTime(startTime);
        vo.setName(name);

        //steam():把一个源数据，可以是集合，数组，I/O channel，产生器generator 等转化成流。
        //map():用于映射每个元素到对应的结果。
        //Collectors(): 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
        vo.setMovieList(movieList.stream().map(movie -> {
            MovieVO mvo = new MovieVO();
            mvo.setId(movie.getId());
            mvo.setName(movie.getName());
            mvo.setPosterUrl(movie.getPosterUrl());
            mvo.setDirector(movie.getDirector());
            mvo.setScreenWriter(movie.getScreenWriter());
            mvo.setStarring(movie.getStarring());
            mvo.setType(movie.getType());
            mvo.setCountry(movie.getCountry());
            mvo.setLanguage(movie.getLanguage());
            mvo.setStartDate(movie.getStartDate());
            mvo.setLength(movie.getLength());
            mvo.setDescription(movie.getDescription());
            mvo.setStatus(movie.getStatus());
            mvo.setIslike(movie.getIslike());
            mvo.setLikeCount(movie.getLikeCount());
            return mvo;
        } ).collect(Collectors.toList()));
        return vo;
    }
    public Activity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }


}
