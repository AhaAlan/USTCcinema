package com.example.cinema.controller.management;

import com.example.cinema.service.statistics.MovieLikeService;
import com.example.cinema.bean.vo.MovieBatchOffForm;
import com.example.cinema.bean.vo.MovieForm;
import com.example.cinema.service.management.MovieService;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 电影管理
 */
@RestController
public class MovieController {
    @Resource
    private MovieService movieService;
    @Resource
    private MovieLikeService movieLikeService;

    @RequestMapping(value = "/movie/add", method = RequestMethod.POST)
    public ResponseVO addMovie(@RequestBody MovieForm addMovieForm){
        return movieService.addMovie(addMovieForm);
    }

    @RequestMapping(value = "/movie/{id}/{userId}", method = RequestMethod.GET) //RestFul风格
    public ResponseVO searchOneMovieByIdAndUserId(@PathVariable int id, @PathVariable int userId){
        return movieService.searchOneMovieByIdAndUserId(id, userId);
    }

    @RequestMapping(value = "/movie/all", method = RequestMethod.GET)
    public ResponseVO searchAllMovie(){
        //返回结果中包括已经下架的电影
        return movieService.searchAllMovie();
    }

    @RequestMapping(value = "/movie/all/exclude/off", method = RequestMethod.GET)
    public ResponseVO searchOtherMoviesExcludeOff(){
        //返回结果中不包括已经下架的电影
        return movieService.searchOtherMoviesExcludeOff();
    }

    @RequestMapping(value = "/movie/{movieId}/like", method = RequestMethod.POST)
    public ResponseVO likeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLikeService.likeMovie(userId,movieId);
    }
    @RequestMapping(value = "/movie/{movieId}/unlike", method = RequestMethod.POST)
    public ResponseVO unlikeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLikeService.unLikeMovie(userId,movieId);
    }
    @RequestMapping(value = "/movie/{movieId}/like/count", method = RequestMethod.GET)
    public ResponseVO getMovieLikeCounts(@PathVariable int movieId){
        return movieLikeService.getCountOfLikes(movieId);
    }

    @RequestMapping(value = "/movie/{movieId}/like/date", method = RequestMethod.GET)
    public ResponseVO getMovieLikeCountByDate(@PathVariable int movieId){
        return movieLikeService.getLikeNumsGroupByDate(movieId);
    }

    @RequestMapping(value = "/movie/search",method = RequestMethod.GET)
    public ResponseVO getMovieByKeyword(@RequestParam String keyword){
        return movieService.getMovieByKeyword(keyword);
    }

    @RequestMapping(value = "/movie/off/batch",method = RequestMethod.POST)
    public ResponseVO pullOfBatchOfMovie(@RequestBody MovieBatchOffForm movieBatchOffForm){
        return movieService.pullOfBatchOfMovie(movieBatchOffForm);
    }

    @RequestMapping(value = "/movie/update",method = RequestMethod.POST)
    public ResponseVO updateMovie(@RequestBody MovieForm updateMovieForm){
        return movieService.updateMovie(updateMovieForm);
    }

    @RequestMapping(value = "/movie/type/{type}/{state}", method = RequestMethod.GET)
    public ResponseVO selectMovieByType(@PathVariable String type, @PathVariable int state){
        return movieService.selectMovieByType(type,state);
    }

    @RequestMapping(value = "/movie/boxOffice", method = RequestMethod.GET)
    public ResponseVO getBoxOfficeList(){
        return movieService.getBoxOfficeList();
    }

    @RequestMapping(value = "/movie/like", method = RequestMethod.GET)
    public ResponseVO getLikeDataList(){
        return movieService.getLikeDataList();
    }

}
