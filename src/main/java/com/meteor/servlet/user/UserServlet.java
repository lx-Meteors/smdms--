package com.meteor.servlet.user;

import com.meteor.pojo.Role;
import com.meteor.pojo.User;
import com.meteor.service.role.RoleService;
import com.meteor.service.role.RoleServiceImpl;
import com.meteor.service.user.UserService;
import com.meteor.service.user.UserServiceImpl;
import com.meteor.util.Constants;
import com.meteor.util.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/user.do","/query"})
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        System.out.println(method);
        if(method.equals("savepwd") && method != null) {
            this.updatePwd(request, response);
        } else if (method.equals("pwdmodify") && method != null){
            this.pwdModify(request, response);
        } else if (method.equals("query") && method != null) {
            this.query(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    /**
     * 修改密码
     * @param request
     * @param response
     */
    public void updatePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = request.getParameter("newpassword");
        boolean flag = false;
        if(user != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(user.getId(), newpassword);
            if(flag) {
                request.setAttribute("message","修改密码成功，请退出使用新密码登录！");
                request.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                request.setAttribute("message","修改密码失败");
            }
        } else {
            request.setAttribute("message","新密码有问题");
        }
        request.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(request, response);
    }

    /**
     * 通过session验证旧密码
     * @param request
     * @param response
     */
    public void pwdModify(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = request.getParameter("oldpassword");

        //万能的map
        Map<String, String> resultMap = new HashMap<>();

        if(user == null) { //Session过期了
            resultMap.put("result","sessionerror");
        } else if(StringUtils.isNullOrEmpty(oldpassword)) { //输入的密码为空
            resultMap.put("result","error");
        } else {
            String userPassword =  user.getUserPassword();
            if(oldpassword.equals(userPassword)) {
                resultMap.put("result","true");
            } else {
                resultMap.put("result","false");
            }
        }
    }

    public void query(HttpServletRequest request, HttpServletResponse response) {
        //从前端获取数据
        String queryname = request.getParameter("queryname");
        String temp = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserService userService = new UserServiceImpl();
        int pageSize = 5;
        int currentPageNo = 1;

        if(queryname == null) {
            queryname = "";
        }
        if(temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);
        }
        if(pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户总数量
        int userCount = userService.getUserCount(queryname, queryUserRole);
        PageSupport pst = new PageSupport();
        pst.setPageSize(pageSize);
        pst.setCurrentPageNo(currentPageNo);
        pst.setTotalCount(userCount);

        if(currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > userCount){
            currentPageNo = userCount;
        }

        //获取用户列表展示
        List<User> userList = userService.getUserList(queryname, queryUserRole, currentPageNo, pageSize);
        request.setAttribute("userList",userList);
        for (User user : userList) {
            System.out.println(user.getUserName());
        }
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        request.setAttribute("roleList",roleList);

        request.setAttribute("totalCount",userCount);
        request.setAttribute("currentPageNo",currentPageNo);

        //返回前端
        try {
            request.getRequestDispatcher( "/jsp/userlist.jsp").forward(request,response);
            System.out.println("返回前端了吗");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
















