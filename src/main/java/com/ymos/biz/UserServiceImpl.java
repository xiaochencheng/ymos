package com.ymos.biz;

import com.ymos.common.StringUtils;
import com.ymos.entity.Paginator;
import com.ymos.entity.Role;
import com.ymos.entity.User;
import com.ymos.mapper.UserMapper;
import com.ymos.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User>  implements UserService{


    UserMapper userMapper;
    @Resource
    private void setUserMapper(UserMapper userMapper){
        this.mapper=userMapper;
        this.userMapper=userMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    @Override
    public User getUserByName(String username) throws Exception {
        return userMapper.getUserByName(username);
    }

    @Override
    public int save(User user){
        user.setCreateDate(new Date());
        user.setPasswd(StringUtils.md5Encode(user.getPasswd(), user.getUsername()));
        int count=userMapper.insert(user);
        for(Role role : user.getRoles()){
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("user_id", user.getId());
            dataMap.put("role_id", role.getId());
            userMapper.insertUserRole(dataMap);
        }
        return count;
    }

    @Override
    public User queryRolesById(String id) throws Exception {
        User user=userMapper.queryRolesById(id);
        return user;
    }

    @Override
    public List<User> queryOtherUsername(String id) throws Exception {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("id", id);
        return userMapper.queryOtherUsername(dataMap);
    }

    @Override
    public int update(User user) {
        //删除原先的角色
        int count=userMapper.deleteRolesByUserId(user.getId());
        for(Role role : user.getRoles()){
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("user_id", user.getId());
            dataMap.put("role_id", role.getId());
            userMapper.insertUserRole(dataMap);
        }
        userMapper.update(user);
        return count;
    }

    @Override
    public int deleteUserRolesByIds(String ids) throws Exception {
        return userMapper.deleteUserRolesByIds(ids.split("-"));
    }

    @Override
    public int updatePassword(User user) throws Exception {
        return userMapper.updatePassword(user);
    }

    @Override
    public int updateLoginInfo(User user) {
        return userMapper.updateLoginInfo(user);
    }


}
