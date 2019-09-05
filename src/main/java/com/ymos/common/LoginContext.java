package com.ymos.common;

import com.ymos.entity.User;

import javax.servlet.http.HttpSession;


public final class LoginContext {
   public static void login(HttpSession session, User user){
	   session.setAttribute(Constants.USER_SESSION_KEY, user);
	   session.setMaxInactiveInterval(60*60);
   }
   public static User getUser(HttpSession session){
	return (User) session.getAttribute(Constants.USER_SESSION_KEY);
   }
   public static boolean checkLogin(HttpSession session){
	   if (session==null) {
		return false;
	   }else{
		   User user=(User) session.getAttribute(Constants.USER_SESSION_KEY);
		   if (user==null) {
			return false;
		}else{
			return true;
		}
	   }
   }
}
