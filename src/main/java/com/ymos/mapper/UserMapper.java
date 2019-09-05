package com.ymos.mapper;

import com.ymos.entity.User;

import java.util.List;
import java.util.Map;


public interface UserMapper extends BaseMapper<User>{

   User getUserByName(String username);

   int insertUserRole(Map<String, Object> dataMap);

   User queryRolesById(String id);

   List<User> queryOtherUsername(Map<String, Object> dataMap);

   int deleteRolesByUserId(String id);

   int deleteUserRolesByIds(String[] ids);

   int updatePassword(User user);

   int updateLoginInfo(User user);



}
