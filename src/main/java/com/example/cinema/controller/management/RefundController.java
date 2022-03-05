package com.example.cinema.controller.management;

import com.example.cinema.service.management.RefundService;
import com.example.cinema.bean.vo.RefundVO;
import com.example.cinema.bean.base.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class RefundController {
    @Resource
    private RefundService refundService;

    @RequestMapping("/refund/add")
    public ResponseVO addRefund(@RequestBody RefundVO formData){
        return refundService.addRefund(formData);
    }
    @RequestMapping("/refund/get")
    public ResponseVO getRefund(){
        return refundService.selectLatestRefund();
    }
}
