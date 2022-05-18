package com.meteor.service.user;

import com.meteor.dao.BaseDao;
import com.meteor.dao.role.RoleDao;
import com.meteor.dao.role.RoleDaoImpl;
import com.meteor.dao.user.UserDao;
import com.meteor.dao.user.UserDaoImpl;
import com.meteor.pojo.Role;
import com.meteor.pojo.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//业务层调用dao层
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    /**
     * 判断能否登录成功
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode, String password) {
        //调用dao层操作用户方法
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            User user = userDao.getLoginUser(conn, userCode);
            if (user.getUserPassword() == null ? password == null : user.getUserPassword().equals(password)) {
                BaseDao.closeResource(conn, ps, rs);
                System.out.println("登录成功");
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BaseDao.closeResource(conn, null, null);
        System.out.println("密码错误");
        return null;
    }

    /**
     * 判断修改密码是否成功
     * @param id
     * @param password
     * @return
     */
    public Boolean updatePwd(int id, String password) {
        Connection conn = null;
        int i = 0;
        boolean flag = false;
        try {
            conn = BaseDao.getConnection();
            i = userDao.updatePwd(conn, id, password);
            if( i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn,null,null);
        }
        return flag;
    }

    /**
     * 根据名字和角色 sql查询两种方式 方法巧妙 获取用户数量
     * @param username
     * @param userRole
     * @return
     */
    @Override
    public int getUserCount(String username, int userRole) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int userCount = 0;
        try {
            conn = BaseDao.getConnection();
            userCount = userDao.getUserCount(conn, username, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn,ps,rs);
        }
        return userCount;
    }

    /**
     * 获取用户列表
     * @param queryUserName
     * @param queryUserRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection conn = null;
        List<User> userList = null;
        try {
            conn = BaseDao.getConnection();
            userList = userDao.getUserList(conn, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, null, null);
        }
        return userList;
    }
}
