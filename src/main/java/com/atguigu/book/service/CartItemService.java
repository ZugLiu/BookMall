package com.atguigu.book.service;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

public interface CartItemService {
    public void addCartItem(CartItem cartItem);
    public void updateCartItem(CartItem cartItem);
    public void addOrUpdateCartItem(CartItem cartItem, Cart cart);

    // 把t_cart_item表中，userBean = user.id的cartItem都放到Cart对象中去
    public Cart getCart(User user);

    public void delCartItem(Integer cartItemId, User user);
}
