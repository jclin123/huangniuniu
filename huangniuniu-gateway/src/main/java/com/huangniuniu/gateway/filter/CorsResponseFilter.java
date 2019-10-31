package com.huangniuniu.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletResponse;
 
//@Component
public class CorsResponseFilter extends ZuulFilter {
 
	/**
	 *返回布尔值来判断该过滤器是否要执行。可以通过此方法来执行过滤器的有效范围
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}
 
	/**
	 * 具体逻辑
	 */
	@Override
	public Object run() {
 
		RequestContext ctx = RequestContext.getCurrentContext();
 
		HttpServletResponse response = ctx.getResponse();
		// 设置哪个源可以访问我
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 允许哪个方法(也就是哪种类型的请求)访问我
		response.setHeader("Access-Control-Allow-Methods", "*");
		// 允许携带哪个头访问我
		response.setHeader("Access-Control-Allow-Headers", "*");
		//允许携带cookie
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		ctx.setResponse(response);
 
		return null;
	}
 
	/**
	 * 过滤器类型：
	 * pre: 在请求被路由之前调用
	 * route: 在路由请求时被调用
	 * post: 表示在route和error过滤器之后被调用
	 * error: 处理请求发生错误是被调用
	 */
	@Override
	public String filterType() {
		return "post";
	}
 
	/**
	 * 过滤器执行顺序，数值越小优先级越高，不同类型的过滤器，执行顺序的值可以相同
	 */
	@Override
	public int filterOrder() {
		return 1000;
	}
 
}

