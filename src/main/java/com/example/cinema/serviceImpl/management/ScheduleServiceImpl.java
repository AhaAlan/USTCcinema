package com.example.cinema.serviceImpl.management;
import java.util.Date;

import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.*;
import com.example.cinema.dao.po.Hall;
import com.example.cinema.service.management.HallService;
import com.example.cinema.service.management.MovieService;
import com.example.cinema.service.management.ScheduleService;
import com.example.cinema.dao.mapper.management.ScheduleMapper;
import com.example.cinema.dao.po.Movie;
import com.example.cinema.dao.po.ScheduleItem;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ScheduleServiceImpl implements ScheduleService{
    private static final String TIME_CONFLICT_ERROR_MESSAGE = "时间段冲突";
    private static final String CROSS_DAYS_ERROR_MESSAGE = "起止时间不能跨天";
    private static final String DATE_INTERVAL_LESS_THAN_LENGTH_ERROR_MESSAGE = "起止时间段不能少于电影时长或结束时间不能早于开始时间";
    private static final String BEFORE_NOW_TIME_ERROR_MESSAGE = "排片日期不能早于当前时间";
    private static final String BEFORE_START_DATE_ERROR_MESSAGE = "排片时间不能早于电影上映时间";
    private static final String MOVIE_NOT_EXIST_ERROR_MESSAGE = "电影不存在";
    private static final String HALL_NOT_EXIST_ERROR_MESSAGE = "影厅不存在";
    private static final String VIEW_COUNT_ERROR_MESSAGE = "排片可见天数错误";
    private static final String ID_LIST_NULL_ERROR_MESSAGE = "id列表不可为空";
    private static final String VIEW_CONFLICT_ERROR_MESSAGE = "有排片信息已对观众可见，无法删除或修改";

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private MovieService movieService;

    @Resource
    private HallService hallService;

    @Override
    public ResponseVO addSchedule(ScheduleForm scheduleForm) {
        try {
            ResponseVO responseVO = preCheck(scheduleForm);
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            scheduleMapper.insertOneSchedule(scheduleForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateSchedule(ScheduleForm scheduleForm) {
        try {
            ResponseVO responseVO = preCheck(scheduleForm);
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            //在修改时要检查想要修改的排片信息是否已被观众可见，若可见则无法修改
            if(isAudienceCanView(Collections.singletonList(scheduleForm.getId()))){
                return ResponseVO.buildFailure(VIEW_CONFLICT_ERROR_MESSAGE);
            }
            scheduleMapper.updateScheduleById(scheduleForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //辅助函数一：新增或修改排片信息的公共前置检查
    private ResponseVO preCheck(ScheduleForm scheduleForm){
        try {
            //使用 SimpleDateFormat 类的 format(date) 方法来格式化时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 检查排片时间是否早于当前时间
            //Java Date类的before()方法测试日期是否在指定的日期之前。
            if(scheduleForm.getStartTime().before(new Date())){
                return ResponseVO.buildFailure(BEFORE_NOW_TIME_ERROR_MESSAGE);
            }
            // 处理排片跨天错误
            if(!simpleDateFormat.format(scheduleForm.getStartTime()).equals(simpleDateFormat.format(scheduleForm.getEndTime()))){
                return ResponseVO.buildFailure(CROSS_DAYS_ERROR_MESSAGE);
            }

            //检查影厅是否存在
            Hall hall = hallService.getHallById(scheduleForm.getHallId());
            if(hall == null){
                return ResponseVO.buildFailure(HALL_NOT_EXIST_ERROR_MESSAGE);
            }

            // 检查电影是否存在
            Movie movie = movieService.getMovieById(scheduleForm.getMovieId());
            if(movie == null){
                return ResponseVO.buildFailure(MOVIE_NOT_EXIST_ERROR_MESSAGE);
            }

            // 检查电影的上映时间是否和排片时间匹配
            if(scheduleForm.getStartTime().before(movie.getStartDate())){
                return ResponseVO.buildFailure(BEFORE_START_DATE_ERROR_MESSAGE);
            }

            // 校验排片时间段合法性
            int minutes = movie.getLength();
            Calendar calendarStartTime = Calendar.getInstance(); //Calendar.getInstance()不仅能获取当前的时间,还能指定需要获取的时间点
            calendarStartTime.setTime(scheduleForm.getStartTime()); //该方法采用Date类型的一个参数表示要设置的给定日期。
            calendarStartTime.add(Calendar.MINUTE, minutes);//Calendar的add方法来进行对日期参数进行相关的动态改变，这里改变分钟，加上电影时长
            Date endTime = calendarStartTime.getTime();//得到电影结束时间
            if(scheduleForm.getEndTime().before(endTime)){
                return ResponseVO.buildFailure(DATE_INTERVAL_LESS_THAN_LENGTH_ERROR_MESSAGE);
            }

            // 检查该排片时间段是否和其他排片信息冲突
            int id = scheduleForm.getId() == null? 0 : scheduleForm.getId();    //这里传入id，是为了避免两个排片为同一个
            if(0 != scheduleMapper.selectScheduleConflictByHallIdAndTime(scheduleForm.getHallId(), scheduleForm.getStartTime(), scheduleForm.getEndTime(),id).size()){
                return ResponseVO.buildFailure(TIME_CONFLICT_ERROR_MESSAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseVO.buildSuccess();
    }

    //辅助函数二：判断是否排片信息是否已被观众可见
    private boolean isAudienceCanView(List<Integer> scheduleIdList) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        Date endDate = getNumDayAfterDate(today, scheduleMapper.selectView());

        List<ScheduleItem> scheduleList = scheduleMapper.selectScheduleBatch(scheduleIdList);
        for(ScheduleItem s : scheduleList){
            if(s.getEndTime().before(endDate) && s.getEndTime().after(today)){
                return true;
            }
        }
        return false;
    }


    @Override
    public ResponseVO getScheduleById(int id) {
        try {
            ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(id);
            if(scheduleItem != null){
                return ResponseVO.buildSuccess(new ScheduleItemVO(scheduleItem));   //PO转VO
            }else{
                return ResponseVO.buildSuccess(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getScheduleView() {
        try {
            return ResponseVO.buildSuccess(scheduleMapper.selectView());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //观众看到的排片信息
    @Override
    public ResponseVO searchAudienceSchedule(int movieId) {
        try{
            //根据view中设置的排片可见限制
            int days = scheduleMapper.selectView();
            List<ScheduleItem> scheduleItems = scheduleMapper.selectScheduleByMovieId(movieId); //该movieid电影下的排片列表，注意是已经排序的列表 order by end_time asc

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));   //今天的日期
            Date endDate = getNumDayAfterDate(today, days); //加上days后为截止时间

            List<ScheduleItem> result = new ArrayList<>();
            for(ScheduleItem s : scheduleItems){
                if(s.getStartTime().before(endDate) && s.getStartTime().after(new Date())){
                    result.add(s);
                }
            }
            int interval = 1;
            if(result.size() > 0){
                interval = (int)((result.get(result.size() - 1).getStartTime().getTime() - today.getTime()) / (1000 * 3600 * 24)) + 1;  //确定天数
            }

            return ResponseVO.buildSuccess(getScheduleVOList(interval, today, result));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<ScheduleItem> getScheduleByMovieIdList(List<Integer> movieIdList) {
        try {
            return scheduleMapper.selectScheduleByMovieIdList(movieIdList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ScheduleItem getScheduleItemById(int id) {
        try {
            return scheduleMapper.selectScheduleById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO searchScheduleSevenDays(int hallId, Date startDate) {
        try {
            // 处理查询表单的起止时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = simpleDateFormat.parse(simpleDateFormat.format(startDate));
            Date endDate = getNumDayAfterDate(startDate, 7);    //7天后的日期

            // 按照日期整理ScheduleItem
            List<ScheduleItem> scheduleItemList = scheduleMapper.selectSchedule(hallId, startDate, endDate);
            return ResponseVO.buildSuccess(getScheduleVOList(7, startDate, scheduleItemList));
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO setScheduleView(ScheduleViewForm scheduleViewForm) {
        try{
            if(scheduleViewForm.getDay() < 0){
                return ResponseVO.buildFailure(VIEW_COUNT_ERROR_MESSAGE);
            }
            int num = scheduleMapper.selectViewCount();
            if(num == 0){
                scheduleMapper.insertOneView(scheduleViewForm);
            }else if(num == 1){
                scheduleMapper.updateOneView(scheduleViewForm);
            }else {
                return ResponseVO.buildFailure(VIEW_COUNT_ERROR_MESSAGE);
            }
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteBatchOfSchedule(ScheduleBatchDeleteForm scheduleBatchDeleteForm) {
        try{
            List<Integer> scheduleIdList = scheduleBatchDeleteForm.getScheduleIdList();
            if(scheduleIdList.size() == 0){
                return ResponseVO.buildFailure(ID_LIST_NULL_ERROR_MESSAGE);
            }
            if(isAudienceCanView(scheduleIdList)){
                return ResponseVO.buildFailure(VIEW_CONFLICT_ERROR_MESSAGE);
            }
            scheduleMapper.deleteScheduleBatch(scheduleIdList);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<ScheduleListVO> getScheduleVOList(int interval, Date startDate, List<ScheduleItem> scheduleItemList ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ScheduleListVO> res = new ArrayList<>();    //结果集（链表的链表）
        for(int i = 0; i < interval; i++){
            Date date = getNumDayAfterDate(startDate, i);
            ScheduleListVO scheduleListVO = new ScheduleListVO();
            scheduleListVO.setDate(date);
            List<ScheduleItemVO> scheduleItemVOList = scheduleItemList2ScheduleItemVOList(scheduleItemList);    //将ScheduleItem转化为ScheduleItemVO
            List<ScheduleItemVO> scheduleItems = new ArrayList<>();
            for(ScheduleItemVO scheduleItem : scheduleItemVOList){
                Date startTime = simpleDateFormat.parse(simpleDateFormat.format(scheduleItem.getStartTime()));  //SimpleDateFormat中的parse方法把String型的字符串转换成特定格式的date类型
                if(date.equals(startTime)){
                    scheduleItems.add(scheduleItem);
                }
            }
            scheduleListVO.setScheduleItemList(scheduleItems);
            res.add(scheduleListVO);
        }
        return res;
    }

    //获得oldDate日期之后 num天的日期
    private Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }

    //转化类：将ScheduleItem转化为ScheduleItemVO，也是List<ScheduleItem>转化为List<ScheduleItemVO>
    private List<ScheduleItemVO> scheduleItemList2ScheduleItemVOList(List<ScheduleItem> scheduleItemList){
        List<ScheduleItemVO> scheduleItemVOList = new ArrayList<>();
        for(ScheduleItem scheduleItem : scheduleItemList){
            scheduleItemVOList.add(new ScheduleItemVO(scheduleItem));
        }
        return scheduleItemVOList;
    }
}
