package com.example.cinema.service.sales;

import com.example.cinema.bean.base.ResponseVO;

import java.util.List;

public interface OrderFormService {

    ResponseVO addOrderForm(List<Integer> ticketid, int couponId);

    ResponseVO addOrderFormByVipCard(List<Integer> ticketid, int couponId);

    ResponseVO refund(int orderFormId);

    ResponseVO getOrderFormByUser(int userId);
    ResponseVO getOrderFormById(int Id);

}
