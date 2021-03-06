package com.example.cinema.data.promotion;

import com.example.cinema.po.Coupon;
import com.example.cinema.po.CouponUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponMapper {

    int insertCoupon(Coupon coupon);

    List<Coupon> selectCouponByUser(int userId);

    Coupon selectById(int id);

    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);

    List<Coupon> selectAllCoupon();

    List<CouponUser> selectCouponByUserId( @Param("userid") int userid);
}
