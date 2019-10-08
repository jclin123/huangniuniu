package com.huangniuniu.comment.interceptor;

import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.comment.config.JwtProperties;
import com.huangniuniu.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties properties;

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的token
        String token = CookieUtils.getCookieValue(request, properties.getCookieName());

        //解析token，获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, properties.getPublicKey());

        if(userInfo == null){
            return false;
        }

        //将userinfo放入线程局部变量
        THREAD_LOCAL.set(userInfo);

        return true;
    }

    public static UserInfo getuserInfo(){
        return THREAD_LOCAL.get();
    }

    //释放线程局部变量

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程局部变量，因为使用了tomcat的线程池，线程不会结束，也不会释放线程的局部变量，需要手动清除。
        THREAD_LOCAL.remove();
    }
}
