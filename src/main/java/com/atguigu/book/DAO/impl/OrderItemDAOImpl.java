package com.atguigu.book.DAO.impl;

import com.atguigu.book.DAO.OrderItemDAO;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

public class OrderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {

    @Override
    public void addOrderItem(OrderItem orderItem) {
        executeUpdate("insert into t_order_item values (0, ?, ?, ?)", orderItem.getBook().getId(), orderItem.getBuyCount(), orderItem.getOrderBean().getId());
    }

    @Override
    public int getOrderItemNumInAnOrder(OrderBean orderBean) {
        List<OrderItem> orderItemList = executeQuery("select * from t_order_item where orderBean = ?", orderBean.getId());
        int totalOrderItem = 0;
        for (OrderItem orderItem:
             orderItemList) {
            totalOrderItem += orderItem.getBuyCount();
        }
        return totalOrderItem;
    }
}
