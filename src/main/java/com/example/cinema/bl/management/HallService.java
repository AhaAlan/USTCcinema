package com.example.cinema.bl.management;

import com.example.cinema.po.Hall;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.SeatForm;


public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    ResponseVO getHallId();

    ResponseVO setHallseats(Hall hall);

    ResponseVO modifyHall(Hall hall);

    ResponseVO deleteHall(Hall hall);

    ResponseVO getHall(int id);

    ResponseVO checkHall(String id);
}
