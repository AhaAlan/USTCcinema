package com.example.cinema.service.management;

import com.example.cinema.dao.po.Hall;
import com.example.cinema.bean.base.ResponseVO;


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
