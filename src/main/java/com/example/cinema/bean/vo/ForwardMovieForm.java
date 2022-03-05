package com.example.cinema.bean.vo;

//包含喜欢人数的电影表单类
public class ForwardMovieForm {
    //记录喜欢人数
    private int sum;

    private  String name;

    private Integer movieId;

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
