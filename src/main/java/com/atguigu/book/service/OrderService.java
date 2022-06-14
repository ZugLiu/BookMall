package com.atguigu.book.service;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.book.pojo.User;

import java.util.List;

public interface OrderService {
    public void addOrder(OrderBean orderBean);
    public void addOrderItems(OrderBean orderBean);
    public List<OrderBean> getOrderList(User user);
}
