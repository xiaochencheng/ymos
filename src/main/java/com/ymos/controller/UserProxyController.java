package com.ymos.controller;

import com.ymos.common.Constants;
import com.ymos.common.IpUtils;
import com.ymos.common.StringUtils;
import com.ymos.entity.*;
import com.ymos.biz.UserProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/userProxy")
public class UserProxyController extends CUDController<User, UserQuery, UserForm, UserProxyService>{

    UserProxyService userProxyService;

    public UserProxyController() {
        super("userProxy");
    }

    @Autowired
    public void setUserProxyService(UserProxyService userProxyService) {
        this.userProxyService = userProxyService;
        this.service=userProxyService;
    }
    //@Reference
    //private ChannelService channelService;
    //public UserProxyController() {
    //    super("userProxy");
    //}

    @Override
    protected boolean preList(UserQuery query, Paginator paginator, Model model, HttpServletRequest request, HttpServletResponse response) {
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
        return "/userProxy/list";
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
    /**
     * 重写删除用户
     * @param ids
     * @param request
     * @return
     */
    @Override
    protected String delete(@PathVariable("ids") String ids, HttpServletRequest request) {
        try {
            userProxyService.deleteByIds(ids);
            userProxyService.deleteUserRolesByIds(ids);
            return "redirect:/userProxy/list";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回重置密码页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/resetPassword",method= RequestMethod.GET)
    public String resetPassword( String id,Model model){
        model.addAttribute("userId", id);
        return "/userProxy/resetPassword";
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
            User existUser = userProxyService.selectById(user.getId());
            if(existUser.getUsername().equals(user.getPasswd())){
                return new Result<User>().setPrompt("密码不能与用户名一致").setFlag(true);
            }
            user.setUsername(existUser.getUsername());
            user.setPasswd(StringUtils.md5Encode(user.getPasswd(), user.getUsername()));
            userProxyService.updatePassword(user);
            return new Result<User>().setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<User>().setFlag(false);
        }
    }


}
