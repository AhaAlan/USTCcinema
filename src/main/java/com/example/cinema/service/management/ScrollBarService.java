package com.example.cinema.service.management;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScrollBarVO;

public interface ScrollBarService {
    ResponseVO addScrollBarContent(ScrollBarVO scrollBarVO);
    ResponseVO getScrollBarContent();
}
