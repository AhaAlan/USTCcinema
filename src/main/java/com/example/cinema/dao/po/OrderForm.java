package com.example.cinema.dao.po;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单类
 */
public class OrderForm {
    //订单id
    private int orderformId;
    //状态位：0已完成，1已退款
    private int state;
    //此订单的用户id
    private int userId;
    //付款方式：0银行卡， 1会员卡
    private int paypath;
    //使用的优惠券id
    private int couponId;
    //付款时间
    private Timestamp time;
    //订单所含的电影票
    private List<Ticket> tickets;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public int getCouponId(){
        return couponId;
    }

    public void setCouponId(int couponId){
        this.couponId = couponId;
    }

    public List<Ticket> getTickets(){
        return tickets;
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }

    public int getPaypath(){
        return  paypath;
    }

    public void setPaypath(int paypath){
        this.paypath = paypath;
    }

    public int getOrderformId(){
        return orderformId;
    }

    public void setOrderformId(int orderformId) {
        this.orderformId = orderformId;
    }
}
