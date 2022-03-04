package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by HJZ on 2021/10/20.
 */
public interface ActivityService {
    
    ResponseVO publishActivity(ActivityForm activityForm);

    ResponseVO getActivities();




}
