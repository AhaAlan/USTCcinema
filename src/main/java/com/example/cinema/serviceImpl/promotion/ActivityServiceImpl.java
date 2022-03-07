package com.example.cinema.serviceImpl.promotion;

import com.example.cinema.service.promotion.ActivityService;
import com.example.cinema.service.promotion.CouponService;
import com.example.cinema.dao.mapper.promotion.ActivityMapper;
import com.example.cinema.dao.po.Activity;
import com.example.cinema.dao.po.Coupon;
import com.example.cinema.bean.vo.ActivityForm;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    ActivityMapper activityMapper;

    @Resource
    CouponService couponService;

    //声明式事务管理 注解实现@Transactional
    @Override
    @Transactional
    public ResponseVO publishActivity(ActivityForm activityForm) {
        try {
            Activity activity = new Activity(); //新建活动
            activity.setName(activityForm.getName());
            activity.setDescription(activityForm.getName());
            activity.setStartTime(activityForm.getStartTime());
            activity.setEndTime(activityForm.getEndTime());
            ResponseVO vo = couponService.addCoupon(activityForm.getCouponForm());  //获取优惠券
            Coupon coupon = (Coupon) vo.getContent();
            activity.setCoupon(coupon);
            activityMapper.insertActivity(activity);
            if(activityForm.getMovieList()!=null&&activityForm.getMovieList().size()!=0){
                activityMapper.insertActivityAndMovie(activity.getId(), activityForm.getMovieList());
            }
            return ResponseVO.buildSuccess(activityMapper.selectById(activity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("发布活动失败");
        }
    }

    @Override
    public ResponseVO getActivities() {
        try {
            return ResponseVO.buildSuccess(activityMapper.selectActivities().stream().map(Activity::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取活动失败");
        }
    }

}
