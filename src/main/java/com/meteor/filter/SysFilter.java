package com.meteor.filter;

import com.meteor.pojo.User;
import com.meteor.util.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter("/jsp/*")
public class SysFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        if(user == null) { //没登陆的情况下想要直接访问，session 已经被移除了，未登录
            resp.sendRedirect("/smdms/error.jsp");
        } else {// 登录成功了，
            chain.doFilter(request,response);
        }
    }

    public void destroy() {

    }
}
