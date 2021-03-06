package com.example.cinema.bl.promotion;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

/**
 * Created by HJZ on 2021/10/20.
 */
public interface CouponService {

    ResponseVO getCouponsByUser(int userId);

    ResponseVO addCoupon(CouponForm couponForm);

    ResponseVO issueCoupon(int couponId,int userId);

    ResponseVO getAllCoupon();

    ResponseVO insertCoupon(List<Integer> userid, List<Integer> couponid);

}
