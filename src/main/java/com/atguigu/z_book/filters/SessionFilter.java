package com.atguigu.z_book.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// 该过滤器会自动拦截所有对.do和.html的访问
@WebFilter(urlPatterns = {"*.do", "*.html"}, initParams = {
        // 初始参数中包含了白名单。对白名单里面的url的访问会被放行
        @WebInitParam(name = "white", value = "/BookMall_war_exploded/page.do?operate=page&page=user/login,/BookMall_war_exploded/user.do?null," +
                "/BookMall_war_exploded/page.do?operate=page&page=user/regist,"
                )
})
public class SessionFilter implements Filter {

    List<String> whiteList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 加载白名单
        String white = filterConfig.getInitParameter("white");
        String[] whiteArr = white.split(",");
        whiteList = Arrays.asList(whiteArr);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        String str = uri+"?"+queryString;

        System.out.println("request.getRequestURI() = " + req.getRequestURI());
        System.out.println("request.getQueryString() = " + req.getQueryString());

        if(whiteList.contains(str)||uri.equals("/BookMall_war_exploded/user.do")){
            filterChain.doFilter(req, resp);
        }else{
            HttpSession session = req.getSession();
            // 如果当前会话中没有用户信息，则自动跳转到登录界面
            Object currUserObj = session.getAttribute("currUser");
            if(currUserObj == null){
                resp.sendRedirect("page.do?operate=page&page=user/login");
            }else{
                // 有的话就放行
                filterChain.doFilter(req, resp);
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
