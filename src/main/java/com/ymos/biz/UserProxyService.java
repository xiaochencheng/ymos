package com.ymos.biz;


import com.ymos.entity.User;

public interface UserProxyService extends BaseService<User> {
    int deleteUserRolesByIds(String ids);

    int updatePassword(User user);

}
