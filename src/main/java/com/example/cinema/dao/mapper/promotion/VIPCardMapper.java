package com.example.cinema.dao.mapper.promotion;

import com.example.cinema.dao.po.VIPCard;
import com.example.cinema.dao.po.VIPInfo;
import com.example.cinema.bean.vo.VipForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VIPCardMapper {
    //新增一个会员卡，返回一个插入的会员卡的主键 因为xml中使用到了useGeneratedKeys=“true”     keyProperty=“id”
    int insertOneCard(VIPCard vipCard);
    //新增一个会员卡种类
    int insertNewKindCard(VipForm vipInfo);
    //根据会员卡id，更新会员卡，注意这里传入的信息为VIPform，来自VO
    int updateKindCardById(VipForm vipInfo);
    //根据会员卡id，删除会员卡
    void deleteKindCardById(@Param("id") int id);
    //根据会员卡id，查询会员卡
    VIPCard selectCardById(int id);
    //根据会员卡种类，查询会员卡列表（查的是VIPcard表）
    List<Integer> selectCardByKindId(int kind);
    //根据会员卡种类，查询会员卡（查的是VIPinfo表）
    VIPInfo selectCardByKind(int id);
    //根据用户id，查询会员卡
    VIPCard selectCardByUserId(int userId);
    //更新会员卡余额
    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);
    //更新会员卡的支付历史
    void updateChargeHistory(@Param("id") int id,@Param("charge_history") String chargeHistory);
    //显示所有会员卡种类列表
    List<VIPInfo> selectAllVIPInfo();
    //显示系统中所有的会员卡
    List<VIPCard> selectAllVipCard();

}
