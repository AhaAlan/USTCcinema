package com.example.cinema.controller.user;

import com.example.cinema.serviceImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.bean.vo.UserForm;
import com.example.cinema.bean.base.ResponseVO;
import com.example.cinema.bean.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@RestController相当于@Controller和@ResponseBody的快捷方式。
//@Controller是@Component注解的一个延伸，Spring会自动扫描并配置被该注解标注的类。
@RestController
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";

    //@Autowired注解用于标记Spring将要解析和注入的依赖项。
    @Autowired
    private AccountServiceImpl accountService;

    //@PostMapping注解用于处理HTTP POST请求，并将请求映射到具体的处理方法中。 相当于是@RequestMapping(method=HttpMethod.POST)
    //@GetMapping注解用于处理HTTP GET请求，并将请求映射到具体的处理方法中。相当于是@RequestMapping(method=RequestMethod.GET)
    //@RequestBody在处理请求方法的参数列表中使用，它可以将请求主体中的参数绑定到一个对象中
    //@RequestParam注解用于将方法的参数与Web请求的传递的参数进行绑定。
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //如果该用户存在，注册session，并返回成功信息
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }

    //返回首页index.html
    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @PostMapping("/creat")
    public ResponseVO creatWorker(@RequestParam String name, @RequestParam String password){
        return accountService.creatWorker(name, password);
    }

    @PostMapping("/delete")
    public ResponseVO deleteWorker(@RequestParam String name){
        return accountService.deleteWorker(name);
    }

    @PostMapping("/search")
    public ResponseVO searchWorker(@RequestParam String name){
        return accountService.searchWorker(name);
    }

    @PostMapping("/all")
    public ResponseVO getAllWorker(){
        return accountService.getAllWorker();
    }
}
