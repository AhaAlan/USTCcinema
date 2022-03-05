package com.example.cinema.serviceImpl.management;

import com.example.cinema.service.management.HallService;
import com.example.cinema.dao.mapper.management.HallMapper;
import com.example.cinema.dao.mapper.management.ScheduleMapper;
import com.example.cinema.dao.po.Hall;
import com.example.cinema.dao.po.ScheduleItem;
import com.example.cinema.bean.vo.HallVO;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HallServiceImpl implements HallService {
    private static final String DELETE_ERROR_MESSAGE = "否";
    private static final String DELETE_RIGHT_MESSAGE = "可";

    @Resource
    private HallMapper hallMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //插入时，判断是否有空余id可以使用，
    @Override
    public ResponseVO getHallId() {
        try {
            int result=0;//最终结果
            int flag=0; //是否占用：1没占用，0占用
            int index=0;
            List<Hall> hallList=hallMapper.selectAllHall();
            for(int i=1;i<100;i++){
                //只要遇到相等的，说明该id被占用，i立刻进入下一个循环，直到找到一个没被占用的flag==1
                for(int m=0;m<hallList.size();m++){
                    if(i==hallList.get(m).getId()){
                        flag=0;
                        break;
                    }
                    else{
                        flag=1;
                    }
                }
                if(flag==1){
                    index=1;
                    result=i;
                    break;
                }
            }
            if(index==0){   //index=0表示都被占用了，没有新的id了，拓展当前id
                result=hallMapper.selectAllHall().size()+1; //最终的id
            }
            return ResponseVO.buildSuccess(result); //不管index是否为0，都要返回，若index=1，返回循环中找到的i；

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO setHallSeats(Hall hall) {
        try {
            hallMapper.insertHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO modifyHall(Hall hall){
        try {
            hallMapper.updataHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteHall(Hall hall){
        try {
            Hall newhall=new Hall();
            newhall.setId(Integer.valueOf(hall.getName()));
            newhall.setName("none");
            newhall.setColumn(0);
            newhall.setRow(0);
            hallMapper.deleteHall(newhall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO checkHall(String lastid) {
        try {
            Date date=new Date();
            int id=Integer.valueOf(lastid);
            List<ScheduleItem> scheduleItems=scheduleMapper.selectScheduleHall(id,date);//selectScheduleHall查询date的某hall的排片信息（现在这个时刻）
            if(scheduleItems.size()==0){
                return ResponseVO.buildSuccess(DELETE_RIGHT_MESSAGE);   //表示可以排片
            } else{
                return ResponseVO.buildSuccess(DELETE_ERROR_MESSAGE);   //表示当前时间不可以排片
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getHall(int id) {
        try {
            return ResponseVO.buildSuccess(hallMapper.selectHallById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //转化类：将PO转化为VO
    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }
}
