package com.ymos.biz;

import com.ymos.entity.Menu;
import com.ymos.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {

    int deleteUserRolesByIds(String[] ids) throws Exception;

    List<Menu> queryMenusByRoleId(int roleId) throws Exception;

    void savePrivilege(int role_id, String[] menu_ids) throws Exception;

    Role queryByRoleName(String name) throws Exception;

    List<Role> queryOtherRoleName(String id) throws Exception;

}
