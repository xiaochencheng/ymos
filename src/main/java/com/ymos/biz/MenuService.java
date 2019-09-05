package com.ymos.biz;

import com.ymos.entity.Menu;
import com.ymos.entity.Tree;

import java.util.List;

public interface MenuService  extends BaseService<Menu> {

    List<Menu> getMenuByUserId(String userId);

    List<Menu> queryFatherMenu() throws Exception;

    void delete(int id) throws Exception;

    List<Menu> getMenuTree() throws Exception;

    List<Menu> getFatherNode(List<Menu> treesList);

    List<Menu> getChildrenNode(String id, List<Menu> treesList);

    List<Tree> getPrivilegeTree(Integer roleId) throws Exception;

    List<Menu> queryMenuUrl(String id);

    List<Menu> selectAll();

    int save(Menu t);

    //int update(Menu menu);

}
