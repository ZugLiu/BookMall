package com.atguigu.book.service.impl;

import com.atguigu.book.DAO.UserDAO;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.UserService;

public class UserServiceImpl implements UserService {

    UserDAO userDAO = null;

    @Override
    public User login(String uname, String pwd) {
        return userDAO.getUser(uname, pwd);
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }
}
