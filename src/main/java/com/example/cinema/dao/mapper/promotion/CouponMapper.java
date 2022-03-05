package com.example.cinema.dao.mapper.promotion;

import com.example.cinema.dao.po.Coupon;
import com.example.cinema.dao.po.CouponUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CouponMapper {
    //新建优惠券
    int insertCoupon(Coupon coupon);
    //根据用户id，查询该用户所拥有的优惠券列表
    List<Coupon> selectCouponByUser(int userId);
    //根据优惠券id查询
    Coupon selectById(int id);
    //插入一条优惠券和对应用户的信息
    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);
    //删除一条优惠券和对应用户的信息
    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);
    //根据用户id和优惠券使用门槛，查询该用户可使用的优惠券列表
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);
    //查询所有种类的优惠券
    List<Coupon> selectAllCoupon();
    //根据用户id，查询该用户所拥有的优惠券id（查的是couponuser表）
    List<CouponUser> selectCouponByUserId( @Param("userid") int userid);
}
