package com.example.cinema.serviceImpl.promotion;

import com.example.cinema.service.promotion.CouponService;
import com.example.cinema.dao.mapper.promotion.CouponMapper;
import com.example.cinema.dao.mapper.promotion.VIPCardMapper;
import com.example.cinema.dao.po.Coupon;
import com.example.cinema.dao.po.CouponUser;
import com.example.cinema.dao.po.VIPCard;
import com.example.cinema.bean.vo.CouponForm;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    CouponMapper couponMapper;

    @Resource
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            List<Coupon> coupons=new ArrayList<>(); //一个用户可能拥有很多优惠券
            List<CouponUser> couponUsers=couponMapper.selectCouponByUserId(userId);
            for(int i=0;i<couponUsers.size();i++){
                coupons.add(couponMapper.selectById(couponUsers.get(i).getId()));
            }
            return ResponseVO.buildSuccess(coupons);    //返回优惠券列表
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAllCoupon() {
        try {
            List<CouponForm> couponForms=new ArrayList<>();
            List<Coupon> coupons=couponMapper.selectAllCoupon();
            for(int i=0;i<coupons.size();i++){
                CouponForm couponForm=new CouponForm();
                couponForm.setId(coupons.get(i).getId());
                couponForm.setName(coupons.get(i).getName());
                couponForms.add(couponForm);
            }
            return ResponseVO.buildSuccess(couponForms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO insertCoupon(List<Integer> userid, List<Integer> couponid) {
        try {
            if(userid.size()==0 && couponid.size()==0){
                List<Coupon> coupons=couponMapper.selectAllCoupon();
                List<VIPCard> vipCards=vipCardMapper.selectAllVipCard();
                for(int i=0;i<coupons.size();i++){
                    for(int m=0;m<vipCards.size();m++){
                        couponMapper.insertCouponUser(coupons.get(i).getId(),vipCards.get(m).getUserId());
                    }
                }
            }else if(userid.size()==0){ //每种优惠券，给每个会员发一张
                List<VIPCard> vipCards=vipCardMapper.selectAllVipCard();
                for(int i=0;i<couponid.size();i++){
                    for(int m=0;m<vipCards.size();m++){
                        couponMapper.insertCouponUser(couponid.get(i),vipCards.get(m).getUserId());
                    }
                }
            }else if(couponid.size()==0){   //一种优惠券，每个人发一张
                List<Coupon> coupons=couponMapper.selectAllCoupon();
                for(int i=0;i<coupons.size();i++){
                    for(int m=0;m<userid.size();m++){
                        couponMapper.insertCouponUser(coupons.get(i).getId(),userid.get(m));
                    }
                }
            }else{
                for(int i=0;i<couponid.size();i++){
                    for(int m=0;m<userid.size();m++){
                        couponMapper.insertCouponUser(couponid.get(i),userid.get(m));
                    }
                }
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
