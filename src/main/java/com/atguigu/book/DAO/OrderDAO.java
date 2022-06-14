package com.atguigu.book.DAO;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;

import java.util.List;

public interface OrderDAO {
    public int addOrder(OrderBean orderBean);
    public List<OrderBean> getOrderList(User user);
}
