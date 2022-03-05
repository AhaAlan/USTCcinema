package com.example.cinema.dao.mapper.management;

import com.example.cinema.dao.po.Refund;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundMapper {
    //插入退款策略
    int insertRefund(Refund refund);
    //获取最新的退款策略
    Refund getLatestRefund();
}
