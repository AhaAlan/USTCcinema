package com.example.cinema.dao.mapper.user;

import com.example.cinema.dao.po.User;
import com.example.cinema.dao.po.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AccountMapper {

    //创建一个新的账号
    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    //根据用户名查找用户
    public User getAccountByName(@Param("username") String username);

    //查询所有用户
    public List<User> selectAllUser();

    //根据用户id查询用户
    User selectUserById(@Param("id") int id);

    //创建一个新的工作人员账号
    public int createNewWorker(@Param("username") String username, @Param("password") String password);

    //根据用户名删除工作人员
    int deleteWorker(@Param("username") String username);

    //查询所有工作人员
    List<Worker> selectAllWorkers();

    //根据用户名查找工作人员
    Worker getWorkerByName(@Param("username") String username);
}
