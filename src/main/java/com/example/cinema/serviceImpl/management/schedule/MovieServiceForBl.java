package com.example.cinema.serviceImpl.management.schedule;

import com.example.cinema.dao.po.Movie;

/**
 * Created by HJZ on 2021/10/20.
 */
public interface MovieServiceForBl {
    /**
     * 根据id查找电影
     * @param id
     * @return
     */
    Movie getMovieById(int id);
}
