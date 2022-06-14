package com.atguigu.book.service.impl;

import com.atguigu.book.DAO.CartItemDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.BookService;
import com.atguigu.book.service.CartItemService;

import java.util.*;

public class CartItemServiceImpl implements CartItemService {
    CartItemDAO cartItemDAO = null;
    BookService bookService = null;
    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDAO.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);
    }

    @Override
    public void addOrUpdateCartItem(CartItem cartItem, Cart cart) {
        // 如果cart不为空
        if(cart != null){
            Map<Integer, CartItem> cartItemMap = cart.getItems();
            if(cartItemMap== null){
                cartItemMap = new HashMap<>();
            }
            // 如果cart中已经有了cartItem所对应的书
            if(cartItemMap.containsKey(cartItem.getBook().getId())){
                // 该cartItem的buyCount加1
                // 注意：传进来的cartItem是Controller层接收前端信息生成的CartItem对象，其id为null，
                // 我们需要根据这个对象找到cart中对应的cartItem，这个cartItem是有id的（因为cart中的所有信息都是从数据库获得的）
                // 然后更新这个cartItem的信息，这样DAO层才能根据这个cardItem的id去数据库中更新对应的行
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount()+1);
                //cart.getItems().put(cartItemTemp.getBook().getId(), cartItemTemp); // 把传进来的cart对象中的对应的cart item也更新，这样前端能及时显示
                cartItemDAO.updateCartItem(cartItemTemp);
            }else{
            // 否则，在t_cart_item表中新增此行
                cartItemDAO.addCartItem(cartItem);
                //cart.getItems().put(cartItem.getBook().getId(), cartItem);
            }
        }else {  // 如果cart为空，则直接加
            addCartItem(cartItem);
        }
    }

    @Override
    public Cart getCart(User user) {
        List<CartItem> cartItemList = cartItemDAO.getCartItem(user);
        Map<Integer, CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem:
             cartItemList) {
            // 此时，cart中book的数据只有id
            // 这里我们要从数据库中获得book的所有信息，在把它放到cart中，要不然cart.html无法获得book的详细信息。
            Book book = bookService.getBookById(cartItem.getBook().getId());
            cartItem.setBook(book);
            // 当前端改用Vue之后，需要在此处调用getTotalPrice来计算该cartItem的总价，否则这个数据为null，不会发送给前端
            cartItem.getTotalPrice();
            cartItemMap.put(book.getId(), cartItem);
        }
        Cart cart = new Cart();
        cart.setItems(cartItemMap);
        return cart;
    }

    @Override
    public void delCartItem(Integer cartItemId, User user) {
        cartItemDAO.delCartItem(new CartItem(cartItemId));
        // 把当前session中购物车的对应item删掉
        Cart currCart = user.getCart();
        Map<Integer, CartItem> currMap = currCart.getItems();
        List<Integer> toBeDel = new ArrayList<>();
        for (Map.Entry<Integer, CartItem> cartItemEntry:
             currMap.entrySet()) {
            if(Objects.equals(cartItemEntry.getValue().getId(), cartItemId)){
                toBeDel.add(cartItemEntry.getKey());
            }
        }

        for (Integer i:
             toBeDel) {
            currMap.remove(i);
        }
    }
}
