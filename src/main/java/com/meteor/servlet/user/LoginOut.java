package com.meteor.servlet.user;

import com.meteor.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LoginOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(Constants.USER_SESSION);
        System.out.println("移除session，退出系统");
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
