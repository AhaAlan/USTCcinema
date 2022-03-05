package com.example.cinema.dao.mapper.statistics;

import com.example.cinema.dao.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CommentMapper {
    //插入一条评论
    int insertOneComment(Comment comment);

    // 获得某个电影的所有评论
    List<Comment> selectAllComment(@Param("movieId") int movieId);
}
