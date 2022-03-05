package com.example.cinema.dao.mapper.sales;

import com.example.cinema.dao.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;
import java.util.List;


@Mapper
public interface TicketMapper {
    //插入一个电影票
    int insertTicket(Ticket ticket);

    //插入许多电影票
    int insertTickets(List<Ticket> tickets);

    //删除
    void deleteTicket(int ticketId);

    //更新电影票状态
    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    //根据电影票id，查询
    Ticket selectTicketById(int id);

    //根据排片id，查询电影票
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    //根据电影票用户，查询电影票
    List<Ticket> selectTicketByUser(int userId);

    //根据用户id，查询电影票列表
    List<Ticket> selectPayTicketByUser(int userId);

    //根据排片id和座位，查询电影票
    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    //查询date期间的所有电影票票
    List<Ticket> selectTicketByDate(@Param("date") Date date, @Param("nextDate") Date nextDate);

    //定时器
    // cron表达式格式：{秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    //下面表示的是每一分钟触发任务，清理过期的电影票
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

