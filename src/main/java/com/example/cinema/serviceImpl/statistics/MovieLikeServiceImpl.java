package com.example.cinema.serviceImpl.statistics;
import java.util.ArrayList;
import java.util.List;

import com.example.cinema.service.management.MovieService;
import com.example.cinema.service.statistics.MovieLikeService;
import com.example.cinema.dao.mapper.statistics.MovieLikeMapper;
import com.example.cinema.dao.mapper.user.AccountMapper;
import com.example.cinema.dao.po.DateLike;
import com.example.cinema.dao.po.User;
import com.example.cinema.bean.vo.DateLikeVO;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MovieLikeServiceImpl implements MovieLikeService {
    private static final String ALREADY_LIKE_ERROR_MESSAGE = "用户已标记该电影为想看";
    private static final String UNLIKE_ERROR_MESSAGE = "用户未标记该电影为想看";
    private static final String MOVIE_NOT_EXIST_ERROR_MESSAGE = "电影不存在";
    private static final String User_NOT_EXIST_ERROR_MESSAGE = "用户不存在";

    @Resource
    private MovieLikeMapper movieLikeMapper;
    @Resource
    private MovieService movieService;
    @Resource
    private AccountMapper accountMapper;

    @Override
    public ResponseVO likeMovie(int userId, int movieId) {
        //标记用户是否存在
        int flag=0;
        List<User> users=accountMapper.selectAllUser();
        for (User user : users) {
            if (userId == user.getId()) {
                flag = 0;
                break;
            } else {
                flag = 1;
            }
        }
        if (userLikeTheMovie(userId, movieId)) {
            return ResponseVO.buildFailure(ALREADY_LIKE_ERROR_MESSAGE);
        } else if (movieService.getMovieById(movieId) == null) {
            return ResponseVO.buildFailure(MOVIE_NOT_EXIST_ERROR_MESSAGE);
        } else if(flag==1){
            return ResponseVO.buildFailure(User_NOT_EXIST_ERROR_MESSAGE);
        }

        try {
            return ResponseVO.buildSuccess(movieLikeMapper.insertOneLike(movieId, userId));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO unLikeMovie(int userId, int movieId) {
        if (!userLikeTheMovie(userId, movieId)) {
            return ResponseVO.buildFailure(UNLIKE_ERROR_MESSAGE);
        } else if (movieService.getMovieById(movieId) == null) {
            return ResponseVO.buildFailure(MOVIE_NOT_EXIST_ERROR_MESSAGE);
        }
        try {
            return ResponseVO.buildSuccess(movieLikeMapper.deleteOneLike(movieId, userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCountOfLikes(int movieId) {
        try {
            return ResponseVO.buildSuccess(movieLikeMapper.selectLikeNums(movieId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getLikeNumsGroupByDate(int movieId) {
        try {
            return ResponseVO.buildSuccess(dateLikeList2DateLikeVOList(movieLikeMapper.getDateLikeNum(movieId)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //判断用户是否喜欢该电电影
    private boolean userLikeTheMovie(int userId, int movieId) {
        return movieLikeMapper.selectLikeMovie(movieId, userId) != 0;
    }
    

    //带日期的喜爱列表类转对应的VO类
    private List<DateLikeVO> dateLikeList2DateLikeVOList(List<DateLike> dateLikeList){
        List<DateLikeVO> dateLikeVOList = new ArrayList<>();
        for(DateLike dateLike : dateLikeList){
            dateLikeVOList.add(new DateLikeVO(dateLike));
        }
        return dateLikeVOList;
    }
}
