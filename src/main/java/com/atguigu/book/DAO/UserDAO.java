package com.atguigu.book.DAO;

import com.atguigu.book.pojo.User;

public interface UserDAO {
    User getUser(String uname, String pwd);
    void addUser(User user);
}
