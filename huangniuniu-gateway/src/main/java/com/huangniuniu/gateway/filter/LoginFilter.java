package com.huangniuniu.gateway.filter;

import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.common.utils.CookieUtils;
import com.huangniuniu.gateway.config.FilterProperties;
import com.huangniuniu.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        //获取白名单
        List<String> allowPaths = this.filterProperties.getAllowPaths();


        //初始化运行上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = currentContext.getRequest();
        //获取请求路径
        String url = request.getRequestURL().toString();

        //判断请求路径是否存在在白名单中
        // 遍历允许访问的路径
        for (String allowPath : allowPaths){
            if(StringUtils.contains(url,allowPath)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //初始化运行上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = currentContext.getRequest();

        //获取cookie值，即token
        String token1 = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        String token2 = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieUserName());

        if(StringUtils.isBlank(token1) && StringUtils.isBlank(token2)){
            //不转发请求
            currentContext.setSendZuulResponse(false);
            //响应状态码
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

       //校验
        try {
            // 校验通过什么都不做，即放行,这里有问题，先放着
            UserInfo userInfo1 = JwtUtils.getInfoFromToken(token1, this.jwtProperties.getPublicKey());
            UserInfo userInfo2 = JwtUtils.getInfoFromToken(token2, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            //不转发请求
            currentContext.setSendZuulResponse(false);
            //响应状态码
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
