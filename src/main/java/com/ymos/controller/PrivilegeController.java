package com.ymos.controller;


import com.ymos.entity.Menu;
import com.ymos.entity.Result;
import com.ymos.entity.Role;
import com.ymos.biz.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/privilege")
public class PrivilegeController {
    @Autowired
    RoleService roleService;
	/**
	 * 查询所有角色
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET) 
	public String list(Model model){
		try {
			List<Role> roles = roleService.selectAll();
			model.addAttribute("roles", roles);
			return "/system/privilege";
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 根据id查询角色拥有的所有权限
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMmenusByRoleId", method = RequestMethod.GET) 
	public Result<List<Menu>> queryMenusByRoleId(@RequestParam int roleId){
		try {
			return new Result<List<Menu>>().setFlag(true).setData(roleService.queryMenusByRoleId(roleId));
		} catch (Exception e) {
			return new Result<List<Menu>>().setPrompt(e.getMessage());
		} 
	}
	/**
	 * 保存角色的权限
	 * @param role_id
	 * @param menu_ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePrivilege/{role_id}", method = RequestMethod.GET) 
	public Result<Object> savePrivilege(@PathVariable int role_id, @RequestParam String menu_ids){
		try {
			roleService.savePrivilege(role_id, menu_ids.split(","));
			return new Result<Object>().setFlag(true);
		} catch (Exception e) {
			return new Result<Object>().setPrompt(e.getMessage());
		}
	}
}
