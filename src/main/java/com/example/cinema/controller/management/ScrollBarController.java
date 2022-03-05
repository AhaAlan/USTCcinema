package com.example.cinema.controller.management;


import com.example.cinema.service.management.ScrollBarService;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScrollBarVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
public class ScrollBarController {
    @Resource
    private ScrollBarService scrollBarService;

    @RequestMapping("/scroll/bar/add")
    public ResponseVO addHomePageContent(@RequestBody ScrollBarVO formData){
        return scrollBarService.addScrollBarContent(formData);
    }
    @RequestMapping("/scroll/bar/get")
    public ResponseVO getHomePageContent(){
        return scrollBarService.getScrollBarContent();
    }
}