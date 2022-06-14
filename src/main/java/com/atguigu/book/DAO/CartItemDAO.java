package com.atguigu.book.DAO;

import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

import java.util.List;

public interface CartItemDAO {
    public void addCartItem(CartItem cartItem);
    // 只更新cartItem的buyCount
    public void updateCartItem(CartItem cartItem);
    public List<CartItem> getCartItem(User user);
    public void delCartItem(CartItem cartItem);
}
