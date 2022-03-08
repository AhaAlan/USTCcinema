package com.example.cinema.service.statistics;

import com.example.cinema.dao.po.Comment;
import com.example.cinema.bean.base.ResponseVO;


public interface CommentService {
    //插入评论
    ResponseVO insertOneComment(Comment comment);
    //获取所有评论
    ResponseVO selectAllComment(int movieId);
}
