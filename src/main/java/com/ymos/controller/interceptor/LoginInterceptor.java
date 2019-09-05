package com.ymos.controller.interceptor;


import com.ymos.common.Constants;
import com.ymos.entity.Menu;
import com.ymos.entity.User;
import com.ymos.biz.MenuService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Resource
	private MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		String url=request.getRequestURI();
		if(url.endsWith(".ttf")||url.endsWith(".woff")||url.endsWith(".eot")||url.endsWith(".svg")){
			return true;
		}
		if(user==null){
			 	response.setContentType("text/html");
	            response.setCharacterEncoding("utf-8");
	            PrintWriter out = response.getWriter();
	            StringBuilder builder = new StringBuilder();    
	            builder.append("<script type='text/javascript' charset='UTF-8'>");    
	            builder.append("alert('登录已过期，请重新登录！');");    
	            builder.append("parent.window.location.href='"+request.getContextPath()+"/login';");     
	            builder.append("</script>");    
	            out.print(builder.toString());
	            out.close();
			return false;
		}
		List<Menu> list= menuService.queryMenuUrl(user.getId());
		String requestURI = request.getRequestURI();
		int count=0;
		if (requestURI.contains("query")){
			return true;
		}

		for (Menu menu : list) {
			if(requestURI.contains(menu.getUrl().substring(0,menu.getUrl().lastIndexOf("list")))){
				count++;
			}
		}
		if(count==0){
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
