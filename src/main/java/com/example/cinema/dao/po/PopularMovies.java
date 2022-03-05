package com.example.cinema.dao.po;

/**
 * 受喜爱的电影
 */
public class PopularMovies {
    private Integer movieId;
    private String name;
    //票房
    private Integer boxOffice;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}