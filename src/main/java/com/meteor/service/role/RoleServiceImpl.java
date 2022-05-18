package com.meteor.service.role;

import com.meteor.dao.BaseDao;
import com.meteor.dao.role.RoleDao;
import com.meteor.dao.role.RoleDaoImpl;
import com.meteor.pojo.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<Role> getRoleList() {
        Connection conn = null;
        List<Role> roleList = null;
        RoleDao roleDao = new RoleDaoImpl();
        try {
            conn = BaseDao.getConnection();
            roleList = roleDao.getRoleList(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, null, null);
        }
        return roleList;
    }

}
