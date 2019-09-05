package com.ymos.mapper;

import com.ymos.entity.Menu;
import com.ymos.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {

    int deleteUserRolesByIds(String[] ids);

    List<Menu> queryMenusByRoleId(int roleId);

    int deleteRoleMenuById(int role_id);

    int deleteRoleMenusByIds(String[] ids);

    int insertRoleMenu(Map<String, Object> param);

    Role queryByRoleName(String name);

    List<Role> queryOtherRoleName(String id);

}
