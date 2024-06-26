package com.example.cinema.serviceImpl.sales;

import com.example.cinema.bean.vo.OrderFormWithMovieScheduleCouponVO;
import com.example.cinema.bean.vo.OrderFormWithMovieVO;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.TicketVO;
import com.example.cinema.dao.po.*;
import com.example.cinema.service.sales.OrderFormService;
import com.example.cinema.dao.mapper.management.RefundMapper;
import com.example.cinema.dao.mapper.management.ScheduleMapper;
import com.example.cinema.dao.mapper.promotion.CouponMapper;
import com.example.cinema.dao.mapper.promotion.VIPCardMapper;
import com.example.cinema.dao.mapper.sales.OrderFormMapper;
import com.example.cinema.dao.mapper.sales.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class OrderFormServiceImpl implements OrderFormService {
    @Resource
    OrderFormMapper orderFormMapper;
    @Resource
    TicketMapper ticketMapper;
    @Resource
    ScheduleMapper scheduleMapper;
    @Resource
    CouponMapper couponMapper;
    @Resource
    VIPCardMapper vipCardMapper;
    @Resource
    RefundMapper refundMapper;

    @Override
    public ResponseVO addOrderForm(List<Integer> ticketid, int couponId) {
        try{
            OrderForm orderForm = new OrderForm();
            List<Ticket> tickets = new ArrayList<>();
            for(int i=0;i<ticketid.size();i++){
                tickets.add(ticketMapper.selectTicketById(ticketid.get(i)));
            }
            Ticket ticket1 = ticketMapper.selectTicketById(ticketid.get(0));    //ticket1只是用来获取该用户的id
            orderForm.setTime(new Timestamp(new Date().getTime()));
            orderForm.setTickets(tickets);
            orderForm.setUserId(ticket1.getUserId());
            orderForm.setPaypath(0);
            orderForm.setCouponId(couponId);
            orderForm.setState(0);//订单状态
            if(ticket1.getState()==1) {
                orderFormMapper.insertOrderForm(orderForm);
                orderFormMapper.insertOrderFormAndTickets(orderForm.getOrderformId(),ticketid);
                return ResponseVO.buildSuccess();
            }else{
                return ResponseVO.buildFailure("订单创建失败，因为您还未付款");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO addOrderFormByVipCard(List<Integer> ticketid, int couponId) {
        try{
            OrderForm orderForm = new OrderForm();
            List<Ticket> tickets = new ArrayList<>();
            for(int i=0;i<ticketid.size();i++){
                tickets.add(ticketMapper.selectTicketById(ticketid.get(i)));
            }
            Ticket ticket1 = ticketMapper.selectTicketById(ticketid.get(0));
            orderForm.setTime(new Timestamp(new Date().getTime()));
            orderForm.setTickets(tickets);
            orderForm.setUserId(ticket1.getUserId());
            orderForm.setPaypath(1);
            orderForm.setCouponId(couponId);
            orderForm.setState(0);
            if(ticket1.getState()==1) {
                orderFormMapper.insertOrderForm(orderForm);
                orderFormMapper.insertOrderFormAndTickets(orderForm.getOrderformId(),ticketid);
                return ResponseVO.buildSuccess();
            }else{
                return ResponseVO.buildFailure("订单创建失败，因为您还未付款");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO refund(int orderFormId) {
        try{
            OrderForm orderForm = orderFormMapper.selectOrderFormById(orderFormId);
            if(orderForm.getState()==1){
                return  ResponseVO.buildFailure("该订单已完成退款，不可重复退款");
            }
            //银行卡付款
            if(orderForm.getPaypath()==0) {
                orderFormMapper.updateOrderFormState(orderFormId, 1);
                return ResponseVO.buildSuccess();
            }else{
                double refund;
                int ticketNumber = orderForm.getTickets().size();
                Ticket ticket1 = orderForm.getTickets().get(0);
                Coupon coupon = couponMapper.selectById(orderForm.getCouponId());
                if(coupon==null){
                    refund = (scheduleMapper.selectScheduleById(ticket1.getScheduleId()).getFare()) * ticketNumber;
                }else {
                    refund = (scheduleMapper.selectScheduleById(ticket1.getScheduleId()).getFare()) * ticketNumber - coupon.getDiscountAmount();
                }
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                Refund refund1 = refundMapper.getLatestRefund();
                if(refund1!=null) {
                    Timestamp timeNow = new Timestamp(new Date().getTime());
                    Timestamp timeMovie = new Timestamp(scheduleMapper.selectScheduleById(orderFormMapper.selectOrderFormById(orderFormId).getTickets().get(0).getScheduleId()).getStartTime().getTime());
                    int n = (int) (timeMovie.getTime() - timeNow.getTime()) / (1000 * 60 * 60);
                    int timeOne = refund1.getTimeOne();
                    int timeTwo = refund1.getTimeTwo();
                    int deadline = refund1.getDeadline();
                    if (n < deadline) {
                        return ResponseVO.buildFailure("此订单已超过退票最后时间，无法退票");
                    }else if (n < timeTwo) {
                        double deadlinePercent = refund1.getDeadlinePercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * deadlinePercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }else if (n < timeOne & n >= timeTwo) {
                        double timeTwoPercent = refund1.getTimeTwoPercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * timeTwoPercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }else {
                        double timeOnePercent = refund1.getTimeOnePercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * timeOnePercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }
                }else{
                    Timestamp timeNow = new Timestamp(new Date().getTime());
                    Timestamp timeMovie = new Timestamp(scheduleMapper.selectScheduleById(orderFormMapper.selectOrderFormById(orderFormId).getTickets().get(0).getScheduleId()).getStartTime().getTime());
                    int n = (int) (timeMovie.getTime() - timeNow.getTime());
                    if(n>0){
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }else{
                        return ResponseVO.buildFailure("该电影已上映，无法退款");
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("退款失败");
        }
    }

    @Override
    public ResponseVO getOrderFormByUser(int userId){
        try {
            //该用户的所有订单信息，存放在list中
            List<OrderForm> orderForms = orderFormMapper.selectOrderFormByUserId(userId);
            if(orderForms.size()==0){
                return ResponseVO.buildSuccess("您还没有购买过电影票哦 ");
            }
            else {
                //获取订单列表的首个订单，查找该订单的电影票列表的首个电影票的排片id，根据该id获取电影名
                String movieName = scheduleMapper.selectScheduleById(orderForms.get(0).getTickets().get(0).getScheduleId()).getMovieName();
                //本质是把List<OrderForm>转换为List<OrderFormWithMovieVO>
                List<OrderFormWithMovieVO> orderFormVOList = new ArrayList<>();
                for (int i = 0; i < orderForms.size(); i++) {
                    OrderFormWithMovieVO orderFormWithMovieVO = new OrderFormWithMovieVO();
                    orderFormWithMovieVO.setCouponId(orderForms.get(i).getCouponId());
                    orderFormWithMovieVO.setOrderformId(orderForms.get(i).getOrderformId());
                    orderFormWithMovieVO.setPaypath(orderForms.get(i).getPaypath());
                    orderFormWithMovieVO.setState(orderForms.get(i).getState());
                    //这里本质上是把Ticket转化为TicketVO
                    List<TicketVO> ticketVOList = new ArrayList<>();
                    for (int j = 0; j < orderForms.get(i).getTickets().size(); j++) {
                        ticketVOList.add(orderForms.get(i).getTickets().get(j).getVO());
                    }
                    orderFormWithMovieVO.setTicketNumber(ticketVOList.size());
                    orderFormWithMovieVO.setTickets(ticketVOList);
                    orderFormWithMovieVO.setMovieName(movieName);
                    orderFormWithMovieVO.setTime(orderForms.get(i).getTime());
                    orderFormWithMovieVO.setUserId(orderForms.get(i).getUserId());
                    orderFormVOList.add(orderFormWithMovieVO);
                }
                return ResponseVO.buildSuccess(orderFormVOList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getOrderFormById(int Id){
        try {
            //获取对应id的订单类
            OrderForm orderForm = orderFormMapper.selectOrderFormById(Id);
            String movieName = scheduleMapper.selectScheduleById(orderForm.getTickets().get(0).getScheduleId()).getMovieName();
            //本质上是OrderForm转换为orderFormWithMovieScheduleCouponVO类
            OrderFormWithMovieScheduleCouponVO orderFormWithMovieScheduleCouponVO = new OrderFormWithMovieScheduleCouponVO();
            orderFormWithMovieScheduleCouponVO.setCouponId(orderForm.getCouponId());
            orderFormWithMovieScheduleCouponVO.setOrderformId(orderForm.getOrderformId());
            orderFormWithMovieScheduleCouponVO.setPaypath(orderForm.getPaypath());
            orderFormWithMovieScheduleCouponVO.setState(orderForm.getState());
            List<TicketVO> ticketVOS = new ArrayList<>();
            for(int j = 0;j<orderForm.getTickets().size();j++){
                ticketVOS.add(orderForm.getTickets().get(j).getVO());
            }
            orderFormWithMovieScheduleCouponVO.setTicketNumber(ticketVOS.size());
            orderFormWithMovieScheduleCouponVO.setTickets(ticketVOS);
            orderFormWithMovieScheduleCouponVO.setScheduleItem(scheduleMapper.selectScheduleById(orderForm.getTickets().get(0).getScheduleId()));
            orderFormWithMovieScheduleCouponVO.setMovieName(movieName);
            orderFormWithMovieScheduleCouponVO.setTime(orderForm.getTime());
            orderFormWithMovieScheduleCouponVO.setCoupon(couponMapper.selectById(orderForm.getCouponId()));
            orderFormWithMovieScheduleCouponVO.setUserId(orderForm.getUserId());
            return ResponseVO.buildSuccess(orderFormWithMovieScheduleCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
