package com.meteor.dao.user;

import com.meteor.dao.BaseDao;
import com.meteor.pojo.Role;
import com.meteor.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    /**
     * 获取登录用户信息
     * @param conn
     * @param userCode
     * @return user
     */
    public User getLoginUser(Connection conn, String userCode) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String sql = "select * from `smbms_user` where userCode=?";
        User user = null;
        Object[] params = {userCode};
        try {
            resultSet = BaseDao.execute(conn, sql, ps, params, resultSet);
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreateBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn,ps,resultSet);
        }
        return user;
    }

    /**
     * 修改用户密码
     * @param conn
     * @param id
     * @param password
     * @return
     */
    public int updatePwd(Connection conn, int id, String password) {
        PreparedStatement ps = null;
        String sql = "update smbms_user set userPassword=? where id=?";
        Object[] params = {password, id};
        int i = 0;
        if (conn != null) {
            try {
                i = BaseDao.execute(conn, sql, ps, params);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(conn,ps,null);
            }
        }
        return i;
    }

    /**
     * 根据用户姓名或者角色信息查询 用户数量
     * @param conn
     * @param username
     * @param userRole
     * @return
     */
    @Override
    public int getUserCount(Connection conn, String username, int userRole) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        if(conn != null) {
            ArrayList<Object> list = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) from smbms_user,smbms_role where smbms_user.userRole = smbms_role.id");

            if(!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and smbms_user.userName like ?"); //模糊查询
                list.add("%"+username+"%");
            }

            if(userRole > 0) {
                sql.append(" and smbms_user.userRole = ?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            try {
                rs = BaseDao.execute(conn, sql.toString(), ps, params, rs);
                if(rs.next()) {
                    //这里为什么是从count 查询所有数量，数据库中也没有count啊？？？？？？？？？？？？？？？？？？？？？？？？？？
                    System.out.println(sql.toString());
                    count = rs.getInt("count(1)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BaseDao.closeResource(conn, ps, rs);
        }
        return count;
    }

    /**
     * 获取用户信息列表
     * @param conn
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<User> getUserList(Connection conn, String userName, int userRole, int currentPageNo, int pageSize) {
        //用来存放user对象信息的
        ArrayList<User> userList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        if(conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select smbms_user.*,smbms_role.roleName from smbms_user,smbms_role where smbms_user.userRole = smbms_role.id");
            //用来拼接sql语句的
            ArrayList<Object> list = new ArrayList<>();
            if(!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and smbms_user.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole > 0) {
                sql.append(" and smbms_user.userRole = ?");
                list.add(userRole);
            }
            //---------------------------------------------------------------------------------------
            sql.append(" order by creationDate DESC limit ?,?");
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            try {
                rs = BaseDao.execute(conn, sql.toString(), ps, params, rs);
                while(rs.next()) {
                    User newUser = new User();
                    newUser.setId(rs.getInt("id"));
                    newUser.setUserCode(rs.getString("userCode"));
                    newUser.setUserName(rs.getString("userName"));
                    newUser.setGender(rs.getInt("gender"));
                    newUser.setBirthday(rs.getDate("birthday"));
                    newUser.setPhone(rs.getString("phone"));
                    newUser.setUserRole(rs.getInt("userRole"));
                    newUser.setUserRoleName(rs.getString("roleName"));
                    userList.add(newUser);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(conn, ps , rs);
            }
        }
        return userList;
    }

}
















