package com.example.cinema.dao.mapper.sales;
import com.example.cinema.dao.po.OrderForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface OrderFormMapper {
    //插入一条订单信息
    int insertOrderForm(OrderForm orderForm);

    //根据订单id和电影票id，插入订单id和该id下的所有电影票id
    int insertOrderFormAndTickets(@Param("orderFormId") int orderFormId,@Param("ticketId") List<Integer> ticketId);

    //根据id，查询该订单
    OrderForm selectOrderFormById(int id);

    //根据用户id，查询该用户的所有订单
    List<OrderForm> selectOrderFormByUserId(int userId);

    //根据订单id，更新该订单状态
    int updateOrderFormState(@Param("orderFormId") int orderFormId,@Param("state") int state);
}
