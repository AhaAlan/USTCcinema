package com.example.cinema.controller.statistics;

import com.example.cinema.service.statistics.CommentService;
import com.example.cinema.dao.po.Comment;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CommentController {
    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/comment/insert", method = RequestMethod.POST)
    public ResponseVO insertOneComment(@RequestBody Comment comment){
        return commentService.insertOneComment(comment);
    }

    @RequestMapping(value = "/comment/all/{movieId}", method = RequestMethod.GET)
    public ResponseVO getAllComment(@PathVariable int movieId){
        return commentService.selectAllComment(movieId);
    }












}
