package com.example.cinema.dao.mapper.management;

import com.example.cinema.dao.po.Movie;
import com.example.cinema.bean.vo.MovieForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MovieMapper {
    //插入一条电影信息
    int insertOneMovie(MovieForm addMovieForm);

    //根据id查找电影
    Movie selectMovieById(@Param("id") int id);

    //根据id和userId查找电影
    Movie selectMovieByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    //展示所有电影
    List<Movie> selectAllMovie();

    //展示所有电影(不包括已经下架的)
    List<Movie> selectOtherMoviesExcludeOff();

    //根据关键字搜索电影
    List<Movie> selectMovieByKeyword(@Param("keyword") String keyword);

    //批量更新电影状态
    int updateMovieStatusBatch(List<Integer> movieIdList);

    //修改电影
    int updateMovie(MovieForm updateMovieForm);

    //根据类型查找电影
    List<Movie> selectMovieByType(@Param("type") String type);

}
