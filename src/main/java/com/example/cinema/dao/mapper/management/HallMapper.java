package com.example.cinema.dao.mapper.management;

import com.example.cinema.dao.po.Activity;
import com.example.cinema.dao.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HallMapper {
    //增
    int insertHall(Hall hall);
    //删
    int deleteHall(Hall hall);
    //改
    int updataHall(Hall hall);
    //查询所有影厅信息
    List<Hall> selectAllHall();
    //根据id查询影厅
    Hall selectHallById(@Param("hallId") int hallId);

}
