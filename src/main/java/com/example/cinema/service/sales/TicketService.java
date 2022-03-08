package com.example.cinema.service.sales;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.TicketForm;

import java.util.List;


public interface TicketService {
    //锁座【增加票但状态为未付款】
    ResponseVO addTicket(TicketForm ticketForm);

    //完成购票【不使用会员卡】，流程包括校验优惠券和根据优惠活动赠送优惠券
    ResponseVO completeTicket(List<Integer> id, int couponId);

    //获得该场次的被锁座位和场次信息
    ResponseVO getBySchedule(int scheduleId);

    //获得用户买过的票
    ResponseVO getTicketByUser(int userId);

    //完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
    ResponseVO completeByVIPCard(List<Integer> id, int couponId);

    //取消锁座（只有状态是"锁定中"的可以取消）
    ResponseVO cancelTicket(List<Integer> id);
}
