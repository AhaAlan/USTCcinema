package com.example.cinema.controller.promotion;

import com.example.cinema.service.promotion.CouponService;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    CouponService couponService;

    //@PathVariable接收请求路径中占位符的值，这里是{userId}
    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @GetMapping("/all")
    public ResponseVO getAllCoupon(){
        return couponService.getAllCoupon();
    }

    @PostMapping("/insert")
    public ResponseVO insertCoupon(@RequestParam List<Integer> userid, @RequestParam List<Integer> couponid){
        return couponService.insertCoupon(userid, couponid);
    }

}
