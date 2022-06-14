package com.atguigu.book.controller;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.atguigu.book.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController {
    UserService userService = null;
    CartItemService cartItemService = null;
    public String login(String uname, String pwd, HttpSession session){
        User user = userService.login(uname, pwd);
        // 如果登录成功，保存用户信息至session，重定向至bookController，加载所有书籍信息
        if(user != null){
            session.setAttribute("currUser", user);

            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);
            return "redirect:book.do";
        }
        // 如果登录失败，返回登录界面
        return "user/login";
    }
    public String regist(String uname, String pwd, String pwdConfirm, String email, String verifyCode, HttpSession session, HttpServletResponse response) throws IOException {
        Object kaptcha = session.getAttribute("KAPTCHA_SESSION_KEY");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(!verifyCode.equals(kaptcha)){
            // 验证失败，注册不成功
            out.println("<script language='javascript'>alert('验证码不正确！');</script>");
            return "user/regist";

        }
        if(!pwd.equals(pwdConfirm)){
            out.println("<script language='javascript'>alert('两次密码不匹配！');</script>");
            return "user/regist";
        }

        User newUser = new User();
        newUser.setUname(uname);
        newUser.setPwd(pwd);
        newUser.setEmail(email);
        userService.addUser(newUser);
        return "user/login";
    }
}
