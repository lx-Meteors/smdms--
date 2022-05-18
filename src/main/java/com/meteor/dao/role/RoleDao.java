package com.meteor.dao.role;

import com.meteor.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {
    //查询角色列表
    public List<Role> getRoleList(Connection conn);
}
