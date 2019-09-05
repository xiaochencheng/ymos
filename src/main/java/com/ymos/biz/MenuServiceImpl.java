package com.ymos.biz;

import com.ymos.entity.Menu;
import com.ymos.entity.Tree;
import com.ymos.mapper.MenuMapper;
import com.ymos.mapper.RoleMapper;
import com.ymos.biz.MenuService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    MenuMapper menuMapper;

    RoleMapper roleMapper;

    @Resource
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
        this.mapper=menuMapper;
    }

    @Resource
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public MenuMapper getMenuMapper() {
        return menuMapper;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    @Override
    public List<Menu> getMenuByUserId(String userId) {
        return this.menuMapper.getMenuByUserId(userId);
    }

    @Override
    public List<Menu> queryFatherMenu() throws Exception {
        return menuMapper.queryFatherMenu();
    }

    @Override
    public void delete(int id) throws Exception {
        int count=menuMapper.selectCount(id);
        if(count==0){
            menuMapper.delete(id);
            menuMapper.deleteRoleMenuByMenuId(id);
        }else{
            throw new Exception("请先删除子菜单");
        }
    }

    @Override
    public List<Menu> getMenuTree() throws Exception {
        //用于接收数据库查询到的数据
        List<Menu> list = new ArrayList<Menu>();
        //用于把接收到的数据改造成layui tree认识的数据格式
        List<Menu> menuTree = new ArrayList<Menu>();
        list = menuMapper.selectAll();
        menuTree = getFatherNode(list);
        return menuTree;
    }

    @Override
    public List<Menu> getFatherNode(List<Menu> treesList) {
        List<Menu> newTrees = new ArrayList<Menu>();
        for (Menu mt : treesList) {
            if(mt.getPid().equals("0")){///如果pId为0，则该节点为父节点
                //递归获取父节点下的子节点
                mt.setChildren(getChildrenNode(mt.getId(),treesList));
                //mt.setSpread(true);
                newTrees.add(mt);
            }
        }
        return newTrees;
    }

    @Override
    public List<Menu> getChildrenNode(String id, List<Menu> treesList) {
        List<Menu> newTrees = new ArrayList<Menu>();
        for (Menu mt : treesList) {
            if (mt.getPid().equals("0")) continue;
            if(mt.getPid().equals(id)){
                //递归获取子节点下的子节点，即设置树控件中的children
                mt.setChildren(getChildrenNode(mt.getId(), treesList));
                //mt.setSpread(true);
                newTrees.add(mt);
            }
        }
        return newTrees;
    }

    /**
     * 获取权限树
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<Tree> getPrivilegeTree(Integer roleId) throws Exception {
        List<Tree> treeList = new ArrayList<Tree>();
        List<Menu> menuTree = getMenuTree();
        List<Menu> roleMenus=null;
        if(roleId!=null){
            roleMenus = roleMapper.queryMenusByRoleId(roleId);
        }
        Tree t1 = null;
        Tree t2 = null;
        for (Menu m1 : menuTree) {
            t1 = new Tree();//一级菜单
            t1.setId(m1.getId());
            t1.setTitle(m1.getName());
            t1.setValue(m1.getId());
            for (Menu m2 : m1.getChildren()) {
                t2 = new Tree();//二级菜单
                t2.setId(m2.getId());
                t2.setTitle(m2.getName());
                t2.setValue(m2.getId());
                if(roleMenus!=null){
                    for (Menu menu : roleMenus) {
                        if(menu.getId().equals(m2.getId())){
                            t2.setChecked(true);
                        }
                    }
                }
                t1.getData().add(t2);
            }
            treeList.add(t1);
        }
        return treeList;
    }

    @Override
    public List<Menu> queryMenuUrl(String id) {
        return menuMapper.queryMenuUrl(id);
    }

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

}
