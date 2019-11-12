package com.ymos.controller;

import com.ymos.biz.MenuService;
import com.ymos.entity.Menu;
import com.ymos.entity.Result;
import com.ymos.entity.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController{

    MenuService menuService;


    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 返回菜单页面
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "/system/menu";
    }

    /**
     * 查询菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Menu> queryAll(Model model) {
        try {
            return menuService.selectAll();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create")
    public Result<Menu> menu_create(Menu menu) {
        try {
            menuService.save(menu);
            return new Result<Menu>().setFlag(true);
        } catch (Exception e) {
            return new Result<Menu>().setPrompt(e.getMessage());
        }
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modify")
    public Result<Menu> menu_modify(Menu menu) {
        try {
            menuService.update(menu);
            return new Result<Menu>().setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<Menu>().setPrompt(e.getMessage());
        }

    }

    /**
     * 查询父菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFatherMenu")
    public List<Menu> queryFatherMenu() {
        try {
            return menuService.queryFatherMenu();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Result<Menu> menu_delete(@PathVariable int id) {
        try {
            this.menuService.delete(id);
            return new Result<Menu>().setFlag(true);
        } catch (Exception e) {
            return new Result<Menu>().setPrompt(e.getMessage());
        }
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMenuTree")
    public List<Menu> getMenuTree() {
        try {
            return menuService.getMenuTree();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取权限树
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPrivilegeTree")
    public List<Tree> getPrivilegeTree(Integer roleId) {
        try {
            return menuService.getPrivilegeTree(roleId);
        } catch (Exception e) {
            return null;
        }
    }


}
