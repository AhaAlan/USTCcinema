package com.example.cinema.dao.po;

import com.example.cinema.bean.vo.RefundVO;

/**
 * 退款类
 */
public class Refund {
    //退款时间1
    private int timeOne;
    //退款时间2
    private int timeTwo;
    //最迟退款时间
    private int deadline;
    //退款时间1前，退款百分比
    private double timeOnePercent;
    //退款时间2前，退款百分比
    private double timeTwoPercent;
    //最迟退款时间前，退款百分比
    private double deadlinePercent;

    public double getDeadlinePercent() {
        return deadlinePercent;
    }

    public double getTimeTwoPercent() {
        return timeTwoPercent;
    }

    public double getTimeOnePercent() {
        return timeOnePercent;
    }

    public int getTimeOne() {
        return timeOne;
    }

    public int getTimeTwo() {
        return timeTwo;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setDeadlinePercent(double deadlinePercent) {
        this.deadlinePercent = deadlinePercent;
    }

    public void setTimeOne(int timeOne) {
        this.timeOne = timeOne;
    }

    public void setTimeOnePercent(double timeOnePercent) {
        this.timeOnePercent = timeOnePercent;
    }

    public void setTimeTwo(int timeTwo) {
        this.timeTwo = timeTwo;
    }

    public void setTimeTwoPercent(double timeTwoPercent) {
        this.timeTwoPercent = timeTwoPercent;
    }

    //PO转VO
    public RefundVO getRefundVO(){
        RefundVO refundVO = new RefundVO();
        refundVO.setDeadline(this.deadline);
        refundVO.setDeadlinePercent((float)this.deadlinePercent);
        refundVO.setTimeOne(this.timeOne);
        refundVO.setTimeOnePercent((float)this.timeOnePercent);
        refundVO.setTimeTwo(this.timeTwo);
        refundVO.setTimeTwoPercent((float)this.timeTwoPercent);
        return refundVO;
    }
}
