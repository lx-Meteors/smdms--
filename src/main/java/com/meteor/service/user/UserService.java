package com.meteor.service.user;

import com.meteor.pojo.Role;
import com.meteor.pojo.User;

import java.util.List;

public interface UserService {
    //看是否能够登录成功
    public User login(String userCode, String password);

    //根据id修改密码
    public Boolean updatePwd(int id, String password);

    //查询记录数
    public int getUserCount(String username, int userRole);

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

}
