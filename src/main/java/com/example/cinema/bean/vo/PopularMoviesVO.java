package com.example.cinema.bean.vo;

//受欢迎电影VO类
public class PopularMoviesVO {
    //电影id
    private Integer id;

    //电影名称
    private String name;

    //电影票房
    private Integer boxOffice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBoxOffice(){
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice){
        this.boxOffice=boxOffice;
    }

}
