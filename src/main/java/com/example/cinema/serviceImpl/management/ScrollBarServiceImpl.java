package com.example.cinema.serviceImpl.management;
import com.example.cinema.service.management.ScrollBarService;
import com.example.cinema.dao.mapper.management.ScrollBarMapper;
import com.example.cinema.dao.po.ScrollBar;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScrollBarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrollBarServiceImpl implements ScrollBarService {

    @Resource
    ScrollBarMapper scrollBarMapper;

    @Override
    public ResponseVO addScrollBarContent(ScrollBarVO scrollBarVO) {
        try{
            ScrollBar scrollBar = new ScrollBar();
            scrollBar.setPicture(scrollBarVO.getPicture());
            scrollBar.setToWeb(scrollBarVO.getToWeb());
            scrollBarMapper.insertOneScrollBar(scrollBar);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("添加滚动栏失败");
        }
    }

    @Override
    public ResponseVO getScrollBarContent() {
        try {
            List<ScrollBar> scrollBarList = scrollBarMapper.getLatestSixScrollBar();
            List<ScrollBarVO> scrollBarVOS = new ArrayList<>();
            for(int i =0;i<6;i++){  //这里和前端统一，只存放6个滚动栏
                ScrollBarVO scrollBarVO = new ScrollBarVO();
                scrollBarVO.setPicture(scrollBarList.get(i).getPicture());
                scrollBarVO.setToWeb(scrollBarList.get(i).getToWeb());
                scrollBarVOS.add(scrollBarVO);
            }
            return ResponseVO.buildSuccess(scrollBarVOS);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
