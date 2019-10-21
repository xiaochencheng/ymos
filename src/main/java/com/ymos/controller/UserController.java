package com.ymos.controller;


import com.ymos.common.Constants;
import com.ymos.common.IpUtils;
import com.ymos.common.LoginContext;
import com.ymos.common.StringUtils;
import com.ymos.entity.*;
import com.ymos.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends CUDController<User, UserQuery, UserForm,UserService>{

    @Autowired
    UserService userService;

    public UserController() {
        super("user");
    }

    @Override
    protected boolean preList(UserQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

    @Override
    protected String innerList(int page, UserQuery query, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        //Channel channel = channelService.queryChannelByUserId(user.getUsername());
        //if(channel!=null && "1".equals(channel.getIsProxyChannel())){
        //    query.setProxyChannelId(Integer.parseInt(channel.getId()));
        //}
        Paginator paginator=this.intiPaginator(page, query);
        List<User> datas= Collections.emptyList();
        boolean pre=this.preList(query,paginator, model, request, response);
        if (pre) {
            datas=this.service.select(paginator);
            paginator=this.service.initPage(paginator,datas);
        }
        model.addAttribute("datas",datas);
        model.addAttribute("paginator",paginator);
        model.addAttribute("hasDatas",Boolean.valueOf(datas!=null&&!datas.isEmpty()));
        this.afterList(query, paginator,model, request, response);
        return "/user/list";
    }


    /**
     * 重写修改返回页面
     */
    @Override
    protected String update(@PathVariable String id, Model model, HttpServletRequest request) {
        showRoles(id,model);
        String queryStr= request.getParameter("_queryStr");
        model.addAttribute("queryStr",queryStr);
        return "/user/create";
    }

    /**
     * 重写删除用户
     * @param ids
     * @param request
     * @return
     */
    @Override
    protected String delete(@PathVariable("ids") String ids, HttpServletRequest request) {
        try {
            userService.deleteByIds(ids);
            userService.deleteUserRolesByIds(ids);
            return "redirect:/user/list";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回修改密码页面
     * @return
     */
    @RequestMapping(value="/updatePassword",method=RequestMethod.GET)
    public String updatePassword(){
        return "/user/password";
    }
    /**
     * 返回重置密码页面
     * @param id
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/resetPassword",method=RequestMethod.GET)
    public String resetPassword( String id,Model model){
        model.addAttribute("userId", id);
        return "/user/resetPassword";
    }
    /**
     * 重置密码
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/resetPasswords",method=RequestMethod.POST)
    public Result<User> resetPasswords(User user){
        try {
            User existUser = userService.selectById(user.getId());
            if(existUser.getUsername().equals(user.getPasswd())){
                return new Result<User>().setPrompt("密码不能与用户名一致").setFlag(true);
            }
            user.setUsername(existUser.getUsername());
            user.setPasswd(StringUtils.md5Encode(user.getPasswd(), user.getUsername()));
            userService.updatePassword(user);
            return new Result<User>().setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<User>().setFlag(false);
        }
    }

    /**
     * 返回用户基本资料
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/information",method= RequestMethod.GET)
    public String information(HttpServletRequest request, Model model){
        User user = LoginContext.getUser(request.getSession());
        showRoles(user.getId(),model);
        model.addAttribute("token", "information");
        return "/user/create";
    }

    public void showRoles(String id,Model model){
        try {
            User eixstUser = userService.queryRolesById(id);
            List<Role> roles = eixstUser.getRoles();
            String role = "";
            for (Role existRole : roles) {
                role += existRole.getId() + ",";
            }
            role = role.substring(0, role.lastIndexOf(","));
            model.addAttribute("form", eixstUser);
            model.addAttribute("role", role);
        } catch (Exception e) {

        }
    }

    /**
     * 检验用户名是否存在
     * @param user
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/checkUsername",method=RequestMethod.POST)
    public Result<User> checkUsername(User user,HttpServletRequest request){
        try {
            //新增操作
            if(user.getId()==""){
                User existUser = userService.getUserByName(user.getUsername());
                if(existUser!=null){
                    return new Result<User>().setPrompt("该账号已存在").setFlag(true);
                }
                return new Result<User>().setPrompt("").setFlag(true);
            }else{//修改操作
                List<User> list = userService.queryOtherUsername(user.getId());
                for (User existUser : list) {
                    if(user.getUsername().equals(existUser.getUsername())){
                        return new Result<User>().setPrompt("该账号已存在").setFlag(true);
                    }
                }
                return new Result<User>().setPrompt("").setFlag(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<User>().setPrompt("操作异常").setFlag(false);
        }
    }
    /**
     *检验密码是否正确
     * @param password
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/checkPassword",method=RequestMethod.POST)
    public Result<User> checkPassword(String password,HttpServletRequest request){
        try {
            User user = LoginContext.getUser(request.getSession());
            password = StringUtils.md5Encode(password, user.getUsername());
            if (!password.equals(user.getPasswd())) {
                return new Result<User>().setPrompt("当前密码输入有误").setFlag(true);
            } else {
                return new Result<User>().setPrompt("").setFlag(true);
            }
        } catch (Exception e) {
            return new Result<User>().setPrompt("操作异常").setFlag(false);
        }
    }

    /**
     * 修改密码
     * @param newPassword
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updatePassword",method=RequestMethod.POST)
    public Result<User> updatePassword(String newPassword, HttpServletRequest request){
        try {
            User user = LoginContext.getUser(request.getSession());
            newPassword= StringUtils.md5Encode(newPassword, user.getUsername());
            user.setPasswd(newPassword);
            userService.updatePassword(user);
            return new Result<User>().setFlag(true);
        } catch (Exception e) {
            return new Result<User>().setFlag(false);
        }
    }


    @Override
    protected void innerSave(UserForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {

            String roles = request.getParameter("role");
            String[] role_ids = roles.split(",");
            if (role_ids != null && role_ids.length > 0) {
                List<Role> list = new ArrayList<Role>(role_ids.length);
                for (String rid : role_ids) {
                    list.add(new Role(rid, null));
                }
                form.setRoles(list);
            }
            if (form.getId()=="") {
                form.setLastLoginIp(IpUtils.getIpAddr(request));
            }
            this.service.saveOrUpdate(form.toObj());
        } catch (Exception e) {
            e.printStackTrace();
            errors.addError(new ObjectError("error", "操作失败！"));
        }
    }
}
