package com.example.cinema.service.management;

import com.example.cinema.dao.po.Hall;
import com.example.cinema.bean.base.ResponseVO;


public interface HallService {
    //搜索所有影厅
    ResponseVO searchAllHall();
    //获取可使用的影厅id
    ResponseVO getHallId();
    //设置座位
    ResponseVO setHallSeats(Hall hall);
    //更改
    ResponseVO modifyHall(Hall hall);
    //删除
    ResponseVO deleteHall(Hall hall);
    //根据id获取影厅
    ResponseVO getHall(int id);
    //检查当前时间是否可以排片
    ResponseVO checkHall(String id);
    //搜索影厅，这里返回的是该影厅类
    Hall getHallById(int id);
}
