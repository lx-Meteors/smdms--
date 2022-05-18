package com.meteor.dao.user;

import com.meteor.pojo.Role;
import com.meteor.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    //获取登录用户信息
    public User getLoginUser(Connection conn, String userCode);

    //修改用户密码
    public int updatePwd(Connection conn, int id, String password);

    //根据用户名或者角色查询用户总量
    public int getUserCount(Connection conn, String username, int userRole);

    //查询用户信息列表
    public List<User> getUserList(Connection conn, String username, int userRole, int currentPageNo, int pageSize);

}
