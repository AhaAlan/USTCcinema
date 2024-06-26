package com.example.cinema.bean.vo;
import com.example.cinema.dao.po.ScheduleItem;
import java.sql.Timestamp;

/**
 * 带有排片信息的TicketVO类
 */
public class TicketWithScheduleVO {

    //电影票id
    private int id;
    //用户id
    private int userId;
    //排片信息
    private ScheduleItem schedule;

    private int columnIndex;

    private int rowIndex;

    //订单状态
    private String state;

    private Timestamp time;


    public TicketWithScheduleVO() {
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ScheduleItem getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleItem schedule) {
        this.schedule = schedule;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
