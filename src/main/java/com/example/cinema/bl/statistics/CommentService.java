package com.example.cinema.bl.statistics;

import com.example.cinema.po.Comment;
import com.example.cinema.vo.ResponseVO;

import java.util.Date;

/**
 * Created by HJZ on 2021/10/21.
 */
public interface CommentService {

    ResponseVO insertOneComment(Comment comment);

    ResponseVO selectAllComment(int movieId);
}
