package com.meteor.dao.role;

import com.meteor.dao.BaseDao;
import com.meteor.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    /**
     * 获取角色列表
     * @param conn
     * @return
     */
    @Override
    public List<Role> getRoleList(Connection conn) {
        ArrayList<Role> roleList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from smbms_role";
        Object[] params = {};
        if(conn != null) {
            try {
                rs = BaseDao.execute(conn, sql, ps, params, rs);
                while(rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setRoleCode(rs.getString("roleCode"));
                    role.setRoleName(rs.getString("roleName"));
                    roleList.add(role);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(conn, ps, rs);
            }
        }
        return roleList;
    }
}
