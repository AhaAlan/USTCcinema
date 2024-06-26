package com.example.cinema.serviceImpl.management;

import com.example.cinema.service.management.RefundService;
import com.example.cinema.dao.mapper.management.RefundMapper;
import com.example.cinema.dao.po.Refund;
import com.example.cinema.bean.vo.RefundVO;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RefundServiceImpl implements RefundService {
    //@Autowired通常适用于构造函数，成员变量以及方法上
    //@Resource使用在成员属性和setter方法上。
    @Resource
    RefundMapper refundMapper;

    //本质上是，退款策略类的VO转PO
    @Override
    public ResponseVO addRefund(RefundVO refund1) {
        Refund refund = new Refund();
        refund.setDeadline(refund1.getDeadline());
        refund.setDeadlinePercent(refund1.getDeadlinePercent());
        refund.setTimeOne(refund1.getTimeOne());
        refund.setTimeOnePercent(refund1.getTimeOnePercent());
        refund.setTimeTwo(refund1.getTimeTwo());
        refund.setTimeTwoPercent(refund1.getTimeTwoPercent());
        refundMapper.insertRefund(refund);
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO selectLatestRefund(){
        Refund refund = refundMapper.getLatestRefund();
        if(refund!=null) {
            System.out.println(refund.getDeadline());
            return ResponseVO.buildSuccess(refund.getRefundVO());
        }
        else {
            return ResponseVO.buildSuccess();
        }
    }
}
