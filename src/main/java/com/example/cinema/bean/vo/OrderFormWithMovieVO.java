package com.example.cinema.bean.vo;
import java.sql.Timestamp;
import java.util.List;

//带有电影名的订单类
public class OrderFormWithMovieVO {
    private int userId;
    private String movieName;
    private int orderformId;
    private int state;

    //付款方式：0: 银行卡 1: 会员卡
    private int paypath;

    private int couponId;//该订单使用的优惠券id

    private Timestamp time;//该订单形成的时间

    private List<TicketVO> tickets;//该订单下包含的电影票列表

    private int ticketNumber;//电影票数量

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

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
