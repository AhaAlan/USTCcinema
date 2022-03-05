package com.example.cinema.service.management;

import com.example.cinema.bean.vo.RefundVO;
import com.example.cinema.bean.base.ResponseVO;


public interface RefundService {
    //增加退款策略
    ResponseVO addRefund(RefundVO refund);

    //查询最新的退款策略
    ResponseVO selectLatestRefund();
}
