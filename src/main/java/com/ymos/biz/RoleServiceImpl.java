package com.ymos.biz;


import com.ymos.entity.Menu;
import com.ymos.entity.Role;
import com.ymos.mapper.RoleMapper;
import com.ymos.biz.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {


    RoleMapper roleMapper;

    @Resource
    public void setRoleMapper(RoleMapper roleMapper){
        this.mapper=roleMapper;
        this.roleMapper=roleMapper;
    }

    @Override
    public int deleteByIds(String ids){
        try {
            roleMapper.deleteByIds(ids.split("-"));
            roleMapper.deleteUserRolesByIds(ids.split("-"));
            roleMapper.deleteRoleMenusByIds(ids.split("-"));
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    @Override
    public int deleteUserRolesByIds(String[] ids) throws Exception {
        return roleMapper.deleteUserRolesByIds(ids);
    }

    @Override
    public List<Menu> queryMenusByRoleId(int roleId) throws Exception {
        return roleMapper.queryMenusByRoleId(roleId);
    }

    @Override
    public void savePrivilege(int role_id, String[] menu_ids) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("role_id", role_id);
        //删除
        roleMapper.deleteRoleMenuById(role_id);
        if(menu_ids.length!=1) {
            for (String menu_id : menu_ids) {
                param.put("menu_id", menu_id);
                roleMapper.insertRoleMenu(param);
            }
        }
    }

    @Override
    public Role queryByRoleName(String name) throws Exception {
        return roleMapper.queryByRoleName(name);
    }

    @Override
    public List<Role> queryOtherRoleName(String id) throws Exception {
        return roleMapper.queryOtherRoleName(id);
    }
}
