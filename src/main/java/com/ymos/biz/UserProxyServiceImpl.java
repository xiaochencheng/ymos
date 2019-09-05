package com.ymos.biz;

import com.ymos.common.StringUtils;
import com.ymos.entity.Role;
import com.ymos.entity.User;
import com.ymos.mapper.UserProxyMapper;
import com.ymos.biz.UserProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("userProxyService")
public class UserProxyServiceImpl extends BaseServiceImpl<User> implements UserProxyService {
    UserProxyMapper userProxyMapper;

    @Resource
    public void setUserProxyMapper(UserProxyMapper userProxyMapper) {
        this.userProxyMapper = userProxyMapper;
        this.mapper=userProxyMapper;
    }
    @Override
    public int save(User user) {
        user.setCreateDate(new Date());
        user.setPasswd(StringUtils.md5Encode(user.getPasswd(), user.getUsername()));
        int count=userProxyMapper.insert(user);
        for(Role role : user.getRoles()){
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("user_id", user.getId());
            dataMap.put("role_id", role.getId());
            userProxyMapper.insertUserRole(dataMap);
        }
        return count;
    }

    @Override
    public int deleteUserRolesByIds(String ids) {
        return userProxyMapper.deleteUserRolesByIds(ids.split("-"));
    }
}
