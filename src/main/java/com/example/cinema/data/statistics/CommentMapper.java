package com.example.cinema.data.statistics;

import com.example.cinema.po.Comment;
import com.example.cinema.po.DateLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CommentMapper {

    int insertOneComment(Comment comment);

    /**
     * 获得某个电影的所有评论
     * @param movieId
     * @return
     */
    List<Comment> selectAllComment(@Param("movieId") int movieId);
}
