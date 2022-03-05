package com.example.cinema.dao.po;

/**
 * 电影总票房
 */
public class MovieTotalBoxOffice {
    private Integer movieId;
    private String name;
    //票房(单位：元)，(PS:如果后续数据量大，可自行处理单位，如改成单位：万元)
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
