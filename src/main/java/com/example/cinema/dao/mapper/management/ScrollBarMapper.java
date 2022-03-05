package com.example.cinema.dao.mapper.management;

import com.example.cinema.dao.po.ScrollBar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScrollBarMapper {
    //插入一个滚动栏
    int insertOneScrollBar(ScrollBar scrollBar);
    //获取所有滚动栏
    List<ScrollBar> getLatestSixScrollBar();
}
