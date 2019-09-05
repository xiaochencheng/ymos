package com.ymos.mapper;

import com.ymos.entity.Menu;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu>{

    List<Menu> getMenuByUserId(String userId);
    List<Menu> queryFatherMenu();
    int selectCount(int id);
    int delete(int id);
    int deleteRoleMenuByMenuId(int id);
    List<Menu> queryMenuUrl(String id);
    //List<Menu> selectAll();
    //int update(Menu menu);
}
