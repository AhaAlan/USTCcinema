package com.example.cinema.serviceImpl.statistics;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.dao.po.Comment;
import com.example.cinema.service.statistics.CommentService;

import com.example.cinema.dao.mapper.statistics.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public ResponseVO insertOneComment(Comment comment) {
        try{
            commentMapper.insertOneComment(comment);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("评论发表失败");
        }
    }

    @Override
    public ResponseVO selectAllComment(int movieId) {
        try{
            List<Comment> comments=commentMapper.selectAllComment(movieId);
            return ResponseVO.buildSuccess(comments);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("评论显示失败");
        }
    }

}
