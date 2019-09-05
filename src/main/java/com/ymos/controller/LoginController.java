package com.ymos.controller;

import com.ymos.common.IpUtils;
import com.ymos.common.LoginContext;
import com.ymos.common.StringUtils;
import com.ymos.entity.Menu;
import com.ymos.entity.User;
import com.ymos.biz.MenuService;
import com.ymos.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {


    @Resource
    UserService userService;

    @Resource
    MenuService menuService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;

    }
    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "system/login";
    }

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(HttpSession session){
        return "system/login";
    }
    /**
     * 用户登陆
     * @param username
     * @param passwd
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("passwd") String passwd,Model model, HttpSession session, HttpServletRequest request){
        try {
            User user = userService.getUserByName(username);
            if (user == null) {
                checkUser(username,passwd,model);
                model.addAttribute("message", "该用户名不存在!");
                return "/system/login";
            }
            if (!user.getPasswd().equals(StringUtils.md5Encode(passwd, username))) {
                checkUser(username,passwd,model);
                model.addAttribute("message", "用户名和密码不匹配!");
                return "/system/login";
            }
            user.setLastLoginIp(IpUtils.getIpAddr(request));
            user.setLastLoginTime(new Date());
            userService.updateLoginInfo(user);
            LoginContext.login(session, user);

            showMenus(user,model);
            return "/home";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *回显用户名和密码
     * @param username
     * @param passwd
     * @param model
     */
    public void checkUser(String username,String passwd,Model model){
        User user = new User();
        user.setUsername(username);
        user.setPasswd(passwd);
        model.addAttribute("form", user);

    }
    @RequestMapping(value="/home")
    public String home(Model model, HttpSession session, HttpServletRequest request){
        try {
            User user = LoginContext.getUser(session);
            if (user == null) {
                return "/system/login";
            }
            showMenus(user,model);
            return "/home";
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 菜单数据处理
     * @param user
     * @param model
     */
    public void showMenus(User user,Model model){
        List<Menu> datas = this.menuService.getMenuByUserId(user.getId());
        Map<String, ArrayList<Menu>> map = new HashMap<>();
        for (Menu menu : datas) {
            ArrayList<Menu> al = map.get(menu.getPid());
            if (al == null) {
                al = new ArrayList<>();
            }
            al.add(menu);
            map.put(menu.getPid(), al);
        }
        model.addAttribute("datas", map);
        model.addAttribute("user", user.getUsername());
    }


}
