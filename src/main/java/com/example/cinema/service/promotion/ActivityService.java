package com.example.cinema.service.promotion;

import com.example.cinema.bean.vo.ActivityForm;
import com.example.cinema.bean.base.ResponseVO;


public interface ActivityService {
    //发布活动
    ResponseVO publishActivity(ActivityForm activityForm);
    //获取所有活动
    ResponseVO getActivities();

}
