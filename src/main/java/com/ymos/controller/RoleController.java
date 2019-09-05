package com.ymos.controller;

import com.ymos.common.LoginContext;
import com.ymos.entity.*;
import com.ymos.biz.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController extends CUDController<Role, RoleQuery, RoleForm, RoleService>{

    //@Resource
    RoleService roleService;

    public RoleController() {
        super("role");
    }

    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected void innerSave(RoleForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            //新增角色
            if(form.getId()==""){
                User user = LoginContext.getUser(request.getSession());
                form.setCreator(Integer.parseInt(user.getId()));
                form.setCreateDate(new Date());
            }
            this.service.saveOrUpdate(form.toObj());
        } catch (Exception e) {
            errors.addError(new ObjectError("error", "操作失败"));
        }
    }

    /**
     * 重写删除角色
     */
    @Override
    protected String delete(@PathVariable("ids") String ids, HttpServletRequest request) {
        roleService.deleteByIds(ids);
        return "redirect:/role/list";
    }

    /**
     * 检验角色名是否存在
     */
    @ResponseBody
    @RequestMapping(value="/checkName",method= RequestMethod.POST)
    public Result<Role> checkName(Role role){
        try {
            //新增操作
            if(role.getId()==""){
                Role existRole = roleService.queryByRoleName(role.getName());
                if(existRole!=null){
                    return new Result<Role>().setPrompt("该角色名已存在").setFlag(true);
                }
                return new Result<Role>().setPrompt("").setFlag(true);
            }else{//修改操作
                List<Role> list = roleService.queryOtherRoleName(role.getId());
                for (Role existRole : list) {
                    if(role.getName().equals(existRole.getName())){
                        return new Result<Role>().setPrompt("该角色名已存在").setFlag(true);
                    }
                }
                return new Result<Role>().setPrompt("").setFlag(true);
            }
        } catch (Exception e) {
            return new Result<Role>().setPrompt("操作异常").setFlag(false);
        }
    }
    /**
     * 查询所有角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryAllRole")
    public List<Role> queryAllRole(){
        try {
            return roleService.selectAll();
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    protected boolean preList(RoleQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

}
