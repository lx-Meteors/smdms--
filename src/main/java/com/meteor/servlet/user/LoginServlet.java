package com.meteor.servlet.user;

import com.meteor.pojo.User;
import com.meteor.service.user.UserService;
import com.meteor.service.user.UserServiceImpl;
import com.meteor.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//控制层代码调用业务层代码
@WebServlet({"/login.do"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);
        if(user != null) { //查有此人，可以登录
            //存到session中
            HttpSession session = request.getSession();
            session.setAttribute(Constants.USER_SESSION,user);
            response.sendRedirect("jsp/frame.jsp");
        } else { //没有查到这个人 或者账号密码有问题
            request.setAttribute("error","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
