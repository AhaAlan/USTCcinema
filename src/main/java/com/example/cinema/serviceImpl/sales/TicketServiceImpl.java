package com.example.cinema.serviceImpl.sales;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.*;
import com.example.cinema.dao.po.*;
import com.example.cinema.service.management.HallService;
import com.example.cinema.service.management.ScheduleService;
import com.example.cinema.service.sales.TicketService;
import com.example.cinema.dao.mapper.promotion.ActivityMapper;
import com.example.cinema.dao.mapper.promotion.CouponMapper;
import com.example.cinema.dao.mapper.promotion.VIPCardMapper;
import com.example.cinema.dao.mapper.sales.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {

    @Resource
    TicketMapper ticketMapper;
    @Resource
    ScheduleService scheduleService;
    @Resource
    HallService hallService;
    @Resource
    CouponMapper couponMapper;
    @Resource
    ActivityMapper activityMapper;
    @Resource
    VIPCardMapper vipCardMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try{
            List<Coupon> coupons;
            List<TicketVO> ticketVOList = new ArrayList<>();
            List<SeatForm> seats = ticketForm.getSeats();
            double total = 0;
            for(int i=0;i<seats.size();i++) {   //seats列表里有一个seat就代表买一张票
                Ticket ticket = new Ticket();
                ticket.setColumnIndex(seats.get(i).getColumnIndex());
                ticket.setRowIndex(seats.get(i).getRowIndex());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setTime(new Timestamp(new Date().getTime()));
                ticket.setUserId(ticketForm.getUserId());
                ticket.setState(0);
                ticketMapper.insertTicket(ticket);
                ticketVOList.add(ticket.getVO());
                total +=  scheduleService.getScheduleItemById( ticket.getScheduleId()).getFare();   //计算总价
            }
            coupons = couponMapper.selectCouponByUser(ticketForm.getUserId());
            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();
            ticketWithCouponVO.setTicketVOList(ticketVOList);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTotal(total);
            List<Activity> activities = activityMapper.selectActivitiesByMovie(scheduleService.getScheduleItemById(ticketForm.getScheduleId()).getMovieId());
            ticketWithCouponVO.setActivities(activities);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try {
            int movieid=0;
            double totalfare = 0;
            Ticket ticket1 ;
            //校验优惠券
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);
                Timestamp stime = coupon.getStartTime();
                Timestamp etime = coupon.getEndTime();
                for (int i = 0; i < id.size(); i++) {   //这里id为 ticketid列表
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    Timestamp timestamp = ticket.getTime();
                    movieid = scheduleService.getScheduleItemById(ticket.getScheduleId()).getMovieId();
                    if (timestamp.after(etime) || timestamp.before(stime)) {
                        return ResponseVO.buildFailure("该优惠券不能在当前时间使用");
                    }
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                if (totalfare < coupon.getTargetAmount()) {
                    return ResponseVO.buildFailure("所选商品总价不满足优惠券使用条件");
                }
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                couponMapper.deleteCouponUser(couponId, ticket1.getUserId());   //优惠券使用后，要删除
            }
            //根据优惠活动赠送优惠券
            ticket1 = ticketMapper.selectTicketById(id.get(0));
            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            Timestamp timestamp1 = ticket1.getTime();
            for(int i=0;i<activities.size();i++){
                if(timestamp1.after(activities.get(i).getStartTime())&&timestamp1.before(activities.get(i).getEndTime())){
                    couponMapper.insertCouponUser(activities.get(i).getCoupon().getId(),ticket1.getUserId());
                }
            }
            for(int i =0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.forEach(ticket -> seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1);
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取该场次座位信息失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            //本质上是将List<Ticket>转化为List<TicketWithScheduleVO>
            List<Ticket> tickets= ticketMapper.selectTicketByUser(userId);
            List<TicketWithScheduleVO> ticketWithScheduleVOList=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                //本质上是将Ticket类转化为TicketWithScheduleVO类
                Ticket ticket = tickets.get(i);
                TicketWithScheduleVO tws = new TicketWithScheduleVO();
                tws.setTime(ticket.getTime());
                tws.setSchedule(scheduleService.getScheduleItemById(ticket.getScheduleId()));
                tws.setUserId(ticket.getUserId());
                tws.setState(String.valueOf(ticket.getState()));
                tws.setRowIndex(ticket.getRowIndex());
                tws.setColumnIndex(ticket.getColumnIndex());
                tws.setId(ticket.getId());
                ticketWithScheduleVOList.add(tws);
            }
            return ResponseVO.buildSuccess(ticketWithScheduleVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try {
            int movieid=0;
            double totalfare = 0;
            Ticket ticket1 ;
            //校验优惠券，和使用会员卡购买
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);
                Timestamp stime = coupon.getStartTime();
                Timestamp etime = coupon.getEndTime();
                for (int i = 0; i < id.size(); i++) {
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    Timestamp timestamp = ticket.getTime();
                    movieid = scheduleService.getScheduleItemById(ticket.getScheduleId()).getMovieId();
                    if (timestamp.after(etime) || timestamp.before(stime)) {
                        return ResponseVO.buildFailure("该优惠券不能在当前时间使用");
                    }
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                if (totalfare < coupon.getTargetAmount()) {
                    return ResponseVO.buildFailure("所选商品总价不满足优惠券使用条件");
                }
                totalfare -= coupon.getDiscountAmount();
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                if (vipcard.getBalance() >= totalfare) {
                    vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() - totalfare); //使用会员卡购买，更新会员卡余额
                } else {
                    return ResponseVO.buildFailure("会员卡余额不足");
                }
                couponMapper.deleteCouponUser(couponId, ticket1.getUserId());
            }else{
                for (int i = 0; i < id.size(); i++) {
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                if (vipcard.getBalance() >= totalfare) {
                    vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() - totalfare);
                } else {
                    return ResponseVO.buildFailure("会员卡余额不足");
                }
            }
            //根据优惠活动赠送优惠券
            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            Timestamp timestamp1 = ticket1.getTime();
            for(int i=0;i<activities.size();i++){
                if(timestamp1.after(activities.get(i).getStartTime())&&timestamp1.before(activities.get(i).getEndTime())){
                    couponMapper.insertCouponUser(activities.get(i).getCoupon().getId(),ticket1.getUserId());
                }
            }
            for(int i =0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("赠送优惠券失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            for(int i=0;i<id.size();i++) {
                if(ticketMapper.selectTicketById(id.get(i)).getState()==0 ) {   //订单状态：0：未完成 1：已完成 2:已失效
                    ticketMapper.updateTicketState(id.get(i), 2);
                }
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("取消订票失败");
        }
    }



}
