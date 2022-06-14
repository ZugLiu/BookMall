package com.atguigu.book.service.impl;

import com.atguigu.book.DAO.CartItemDAO;
import com.atguigu.book.DAO.OrderDAO;
import com.atguigu.book.DAO.OrderItemDAO;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl extends BaseDAO<OrderBean> implements OrderService {
    OrderDAO orderDAO = null;
    OrderItemDAO orderItemDAO = null;
    CartItemDAO cartItemDAO = null;
    @Override
    public void addOrder(OrderBean orderBean) {
        // first, add orderBean to database
        int orderBeanId = orderDAO.addOrder(orderBean);
        orderBean.setId(orderBeanId);
        // second, set order item list of that orderBean
        // 从currUser的cart中读取数据，把cart中每个item的信息转换成order item对象的相应信息
        User tempUser = orderBean.getOrderUser();
        Map<Integer, CartItem> cartItemList = tempUser.getCart().getItems();
        List<OrderItem> orderItemList = orderBean.getOrderItemList();
        for (CartItem cartItem:
                cartItemList.values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setBuyCount(cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            orderItemList.add(orderItem);
            orderItemDAO.addOrderItem(orderItem);
            // third, 从数据库中删除cartItem
            cartItemDAO.delCartItem(cartItem);
        }
    }

    @Override
    public void addOrderItems(OrderBean orderBean) {
        for (OrderItem orderItem:
             orderBean.getOrderItemList()) {
            orderItemDAO.addOrderItem(orderItem);
        }
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        List<OrderBean> orderBeanList = orderDAO.getOrderList(user);
        for (OrderBean orderBean:
             orderBeanList) {
            // 查询该订单中书的总数量
            int totalBookCount = orderItemDAO.getOrderItemNumInAnOrder(orderBean);
            orderBean.setTotalBookCount(totalBookCount);
        }
        return orderBeanList;
    }
}
