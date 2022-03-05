package com.example.cinema.controller.management;

import com.example.cinema.service.management.ScheduleService;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.ScheduleBatchDeleteForm;
import com.example.cinema.bean.vo.ScheduleForm;
import com.example.cinema.bean.vo.ScheduleViewForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 排片管理
 */
@RestController
public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;

    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
    public ResponseVO addSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.addSchedule(scheduleForm);
    }

    @RequestMapping(value = "/schedule/update", method = RequestMethod.POST)
    public ResponseVO updateSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.updateSchedule(scheduleForm);
    }

    @RequestMapping(value = "/schedule/search", method = RequestMethod.GET)
    public ResponseVO searchSchedule(@RequestParam int hallId, @RequestParam Date startDate){
        //这里传递startDate参数时，前端传的是用/分隔的时间，例如startDate=2019/04/12，所以在服务类里面进行格式统一
        return scheduleService.searchScheduleSevenDays(hallId, startDate);
    }

    @RequestMapping(value = "/schedule/search/audience", method = RequestMethod.GET)
    public ResponseVO searchAudienceSchedule(@RequestParam int movieId){
        return scheduleService.searchAudienceSchedule(movieId);
    }

    @RequestMapping(value = "/schedule/view/set", method = RequestMethod.POST)
    public ResponseVO setScheduleView(@RequestBody  ScheduleViewForm scheduleViewForm){
        return scheduleService.setScheduleView(scheduleViewForm);
    }

    @RequestMapping(value = "/schedule/view", method = RequestMethod.GET)
    public ResponseVO getScheduleView(){
        return scheduleService.getScheduleView();
    }


    @RequestMapping(value = "/schedule/delete/batch", method = RequestMethod.DELETE)
    public ResponseVO deleteBatchOfSchedule(@RequestBody ScheduleBatchDeleteForm scheduleBatchDeleteForm){
        return scheduleService.deleteBatchOfSchedule(scheduleBatchDeleteForm);
    }

    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public ResponseVO getScheduleById(@PathVariable int id){
        return scheduleService.getScheduleById(id);
    }

}
