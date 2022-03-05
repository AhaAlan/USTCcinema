package com.example.cinema.serviceImpl.user;

import com.example.cinema.service.user.AccountService;
import com.example.cinema.dao.mapper.user.AccountMapper;
import com.example.cinema.dao.po.User;
import com.example.cinema.dao.po.Worker;
import com.example.cinema.bean.vo.UserForm;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String ACCOUNT_NOTEXIST="该账号不存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            int flag=0;
            List<User> users=accountMapper.selectAllUser();
            //遍历搜索，判断是否已经存在这样的账号
            for(int i=0;i<users.size();i++){
                if(userForm.getUsername().equals(users.get(i).getUsername())){
                    flag=1; //存在，标记为置1
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            else{
                accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO creatWorker(String name,String password) {
        try {
            int flag=0;
            List<User> users=accountMapper.selectAllUser();
            for(int i=0;i<users.size();i++){
                if(name.equals(users.get(i).getUsername())){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            else{
                accountMapper.createNewWorker(name,password);
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO deleteWorker(String name) {
        try {
            int i=accountMapper.deleteWorker(name); //调用删除，得到返回值
            if(i==0){
                return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
            }
            else{
                return ResponseVO.buildSuccess();
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public ResponseVO searchWorker(String name) {
        try {
            Worker worker=accountMapper.getWorkerByName(name);
            if(worker==null){
                return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
            }
            else{
                return ResponseVO.buildSuccess(worker);
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {   //如果此用户不存在，或者密码错误
            Worker worker=accountMapper.getWorkerByName(userForm.getUsername());
            if(null==worker || !worker.getPassword().equals(userForm.getPassword())){   //如果此工作人员不存在，或者密码错误
                return null;
            }else{
                User newuser=new User();
                newuser.setId(worker.getId());
                newuser.setUsername("worker");
                newuser.setPassword(worker.getPassword());
                return new UserVO(newuser);
            }
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO getAllWorker(){
        try {
            List<Worker> workers=accountMapper.selectAllWorkers();
            return ResponseVO.buildSuccess(workers);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

}
