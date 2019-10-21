package com.ymos.mapper;

import com.ymos.entity.User;

import java.util.Map;

public interface UserProxyMapper extends BaseMapper<User> {
    int insertUserRole(Map<String, Object> dataMap);

    int deleteUserRolesByIds(String[] ids);

    int updatePassword(User user);
}
