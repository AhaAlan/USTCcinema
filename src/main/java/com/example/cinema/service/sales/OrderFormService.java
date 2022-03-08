package com.example.cinema.service.sales;

import com.example.cinema.bean.base.ResponseVO;

import java.util.List;

public interface OrderFormService {
    //新增订单
    ResponseVO addOrderForm(List<Integer> ticketid, int couponId);
    //新增会员订单，其实没差别，因为会员卡不是折扣卡
    ResponseVO addOrderFormByVipCard(List<Integer> ticketid, int couponId);
    //退单
    ResponseVO refund(int orderFormId);
    //根据用户id获取订单列表
    ResponseVO getOrderFormByUser(int userId);
    //
    ResponseVO getOrderFormById(int Id);

}
