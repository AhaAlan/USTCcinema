package com.example.cinema.interceptor;
import com.example.cinema.config.InterceptorConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实现自己的拦截器
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    /**
     * preHandle：在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器。如果返回false，则中断执行。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
        HttpSession session=httpServletRequest.getSession();
        if(null!=session && null!=session.getAttribute(InterceptorConfiguration.SESSION_KEY)){
            return true;
        }
        httpServletResponse.sendRedirect("/index");
        return false;
    }
}
