package com.example.cinema.controller.promotion;

import com.example.cinema.service.promotion.ActivityService;
import com.example.cinema.bean.vo.ActivityForm;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @PostMapping("/publish")
    public ResponseVO publishActivity(@RequestBody ActivityForm activityForm){
        return activityService.publishActivity(activityForm);
    }
    @GetMapping("/get")
    public ResponseVO getActivities(){
        return activityService.getActivities();
    }


}
