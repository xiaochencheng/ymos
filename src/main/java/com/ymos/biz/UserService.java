package com.ymos.biz;

import com.ymos.entity.User;

import java.util.List;


public interface UserService  extends BaseService<User> {

    User getUserByName(String username) throws Exception;

    User queryRolesById(String id) throws Exception;

    List<User> queryOtherUsername(String id) throws Exception;

    int deleteUserRolesByIds(String ids) throws Exception;

    int updatePassword(User user) throws Exception;

    int updateLoginInfo(User user);

}
