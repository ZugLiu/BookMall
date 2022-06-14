package com.atguigu.book.controller;

import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController {
    OrderService orderService = null;
    public String checkout(HttpSession session){
        // create an order object
        OrderBean orderBean = new OrderBean();
        Date now = new Date();

        int year = now.getYear();
        int month = now.getMonth() ;
        int day = now.getDate();
        int hour = now.getHours();
        int min = now.getMinutes() ;
        int sec = now.getSeconds() ;

        orderBean.setOrderNo(UUID.randomUUID().toString()+"_"+year+month+day+hour+min+sec);

        LocalDateTime ldt = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());

        orderBean.setOrderDate(ldt);
        User tempUser = (User) session.getAttribute("currUser");
        orderBean.setOrderUser(tempUser);
        orderBean.setOrderMoney(tempUser.getCart().getTotalPrice().doubleValue());
        orderBean.setOrderStatus(0);
        // add this order to database
        orderService.addOrder(orderBean);
        return "redirect:order.do?operate=getOrderList";
    }
    public String getOrderList(HttpSession session){
        User tempUser = (User) session.getAttribute("currUser");
        List<OrderBean> orderBeanList = orderService.getOrderList(tempUser);
        session.setAttribute("orderBeanList", orderBeanList);
        return "order/order";
    }
}
