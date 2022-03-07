package com.example.cinema.service.promotion;

import com.example.cinema.bean.vo.VIPCardForm;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.VipForm;


public interface VIPService {
    //用户新买一张kind种类的会员卡
    ResponseVO addVIPCard(int userId,int kind);

    ResponseVO getCardById(int id);
    //添加会员卡信息
    ResponseVO addVIPInfo(VipForm addMovieForm);
    //更新会员卡信息
    ResponseVO updateVIPInfo(VipForm vipForm);
    //删除会员卡，
    ResponseVO deleteVIPInfo(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getChargeHistoryByUserId(int userId);

    ResponseVO getCardByUserId(int userId);

    ResponseVO getVipPaySum();

    ResponseVO getKindById(int KindId);


}
