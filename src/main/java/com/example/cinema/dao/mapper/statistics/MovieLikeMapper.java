package com.example.cinema.dao.mapper.statistics;

import com.example.cinema.dao.po.DateLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MovieLikeMapper {
    //标记想看
    int insertOneLike(@Param("movieId")int movieId ,@Param("userId")int userId);

    //删除想看
    int deleteOneLike(@Param("movieId")int movieId ,@Param("userId")int userId);

    //根据id查找该电影的想看人数
    int selectLikeNums(@Param("movieId") int movieId);

    //根据movieId和userId查找记录
    int selectLikeMovie(@Param("movieId") int movieId ,@Param("userId")int userId);

    //根据id获取该电影的喜爱的人数（按日期统计形成链表）
    List<DateLike> getDateLikeNum(@Param("movieId") int movieId);
}
