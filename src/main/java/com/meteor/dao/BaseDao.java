package com.meteor.dao;

import com.meteor.pojo.User;

import java.sql.*;
import java.util.ResourceBundle;

//操作数据库的工具类
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        driver = bundle.getString("driver");
        url = bundle.getString("url");
        username = bundle.getString("username");
        password = bundle.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库的连接
     * @return conn
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    /**
     * 关闭数据库连接资源
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回数据库查询结果集
     * @param conn
     * @param sql
     * @param ps
     * @param params
     * @param rs
     * @return
     * @throws SQLException
     */
    public static ResultSet execute(Connection conn, String sql, PreparedStatement ps, Object[] params, ResultSet rs) throws SQLException {
        ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
        rs = ps.executeQuery();
        return rs;
    }

    /**
     * 返回数据库查询结果数量
     * @param conn
     * @param sql
     * @param ps
     * @param params
     * @return
     * @throws SQLException
     */
    public static int execute(Connection conn, String sql, PreparedStatement ps, Object[] params) throws SQLException {
        ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i+1,params[i]);
        }
        int i = ps.executeUpdate();
        return i;
    }
}
