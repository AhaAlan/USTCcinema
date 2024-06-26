package com.example.cinema.bean.vo;


import java.sql.Timestamp;
import java.util.List;

//订单类对应的VO
public class OrderFormVO {
    private int userId;
    private int orderformId;
    private int state;

    /**
     * 付款方式
     * 0: 银行卡 1: 会员卡
     */
    private int paypath;

    private int couponId;

    private Timestamp time;

    private List<TicketVO> tickets;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public List<TicketVO> getTickets(){
        return tickets;
    }

    public void setTickets(List<TicketVO> tickets){
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
