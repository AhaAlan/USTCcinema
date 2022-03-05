package com.example.cinema.service.user;

import com.example.cinema.bean.vo.UserForm;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.UserVO;


public interface AccountService {

    //注册账号（根据前端传来的用户表单）
    ResponseVO registerAccount(UserForm userForm);

    //创建工作人员账号
    ResponseVO creatWorker(String name, String password);

    //删除工作人员账号（根据用户名）
    ResponseVO deleteWorker(String name);

    //查询工作人员账号
    ResponseVO searchWorker(String name);

    //用户登录，登录成功会返回一个UserVO，用于保存用户信息在session中
    UserVO login(UserForm userForm);

    //获取所有工作人员
    ResponseVO getAllWorker();

}
