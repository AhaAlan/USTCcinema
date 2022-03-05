package com.example.cinema.service.management;

import com.example.cinema.bean.vo.RefundVO;
import com.example.cinema.bean.base.ResponseVO;


public interface RefundService {
    ResponseVO addRefund(RefundVO refund);
    ResponseVO selectLatestRefund();
}
