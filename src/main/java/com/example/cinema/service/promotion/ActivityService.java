package com.example.cinema.service.promotion;

import com.example.cinema.bean.vo.ActivityForm;
import com.example.cinema.bean.base.ResponseVO;

/**
 * Created by HJZ on 2021/10/20.
 */
public interface ActivityService {
    
    ResponseVO publishActivity(ActivityForm activityForm);

    ResponseVO getActivities();




}
