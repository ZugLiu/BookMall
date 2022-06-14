package com.atguigu.book.controller;

import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

public class CartController {
    CartItemService cartItemService = null;
    public String addBook(Integer bookId, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        CartItem cartItem = new CartItem(new Book(bookId), 1, user);
        cartItemService.addOrUpdateCartItem(cartItem, user.getCart());

        return "redirect:cart.do";
    }

    //myssm：如果请求中operate的值为null,则默认operate = index。
    public String index(HttpSession session){
        User user =(User)session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(user);
        user.setCart(cart);
        session.setAttribute("currUser",user);
        return "cart/cart";
    }

    public String editCart(Integer cartItemId, Integer buyCount, HttpSession session){
        cartItemService.updateCartItem(new CartItem(cartItemId, buyCount));

        // 更新session中cart对象的属性
        Map<Integer, CartItem> cartMap = ((User)session.getAttribute("currUser")).getCart().getItems();
        for (CartItem cartItem:
             cartMap.values()) {
            if(Objects.equals(cartItem.getId(), cartItemId)){
                cartItem.setBuyCount(buyCount);
            }
        }

        return "";
    }

    public String cartInfo(HttpSession session){
        User user = (User)session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(user);
        // 计算购物车中书的数量
        cart.getTotalBookCount();
        // 计算购物车中所有cartItem的总价
        cart.getTotalPrice();
        // 计算购物车书的品种
        cart.getTotalCount();
        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart);
        return "json:"+cartJsonStr;
    }

    public String delCartItem(Integer cartItemId, HttpSession session){
        cartItemService.delCartItem(cartItemId, (User) session.getAttribute("currUser"));
        return "";
    }
}
