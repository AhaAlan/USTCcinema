package com.example.cinema.service.statistics;

import com.example.cinema.dao.po.Comment;
import com.example.cinema.bean.base.ResponseVO;

/**
 * Created by HJZ on 2021/10/21.
 */
public interface CommentService {

    ResponseVO insertOneComment(Comment comment);

    ResponseVO selectAllComment(int movieId);
}
