package com.example.cinema.service.promotion;

import com.example.cinema.bean.vo.CouponForm;
import com.example.cinema.bean.base.ResponseVO;

import java.util.List;


public interface CouponService {
    //根据用户id 获取优惠券
    ResponseVO getCouponsByUser(int userId);
    //新增优惠券种类
    ResponseVO addCoupon(CouponForm couponForm);
    //
    ResponseVO issueCoupon(int couponId,int userId);
    //获取所有优惠券
    ResponseVO getAllCoupon();
    //分发优惠券
    ResponseVO insertCoupon(List<Integer> userid, List<Integer> couponid);

}
