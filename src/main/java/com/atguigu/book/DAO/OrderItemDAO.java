package com.atguigu.book.DAO;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;

public interface OrderItemDAO {
    public void addOrderItem(OrderItem orderItem);
    // 返回一个订单中书的总数量。e.g. 一个订单中有3本《图灵传》，2本《Hegel》，则返回5
    public int getOrderItemNumInAnOrder(OrderBean orderBean);

}
