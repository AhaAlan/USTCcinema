package com.example.cinema.service.management;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScrollBarVO;

public interface ScrollBarService {
    //增加滚动栏内容
    ResponseVO addScrollBarContent(ScrollBarVO scrollBarVO);
    //获取滚动栏内容
    ResponseVO getScrollBarContent();
}
