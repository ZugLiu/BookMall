package com.atguigu.book.pojo;

import java.math.BigDecimal;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> items; // key: bookId, value: CartItem
    private Integer totalCount; //购物车中的商品种类
    private BigDecimal totalPrice = BigDecimal.valueOf(0); // 购物车中商品总金额
    private Integer totalBookCount; // 购物车中商品总数量，而不是种类数量

    public Cart() {
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Integer getTotalCount() {
        totalCount = 0;
        if(items != null && items.size() != 0){
            totalCount = items.size();
        }
        return totalCount;
    }


    public BigDecimal getTotalPrice() {
        if(items != null && items.size() != 0){
            for (Map.Entry<Integer, CartItem> entry:
                 items.entrySet()) {
                CartItem book = entry.getValue();
                BigDecimal bigDecimalBookPrice = new BigDecimal(""+book.getBook().getPrice());
                BigDecimal bigDecimalBuyCount = new BigDecimal("" + book.getBuyCount());
                BigDecimal bigDecimalMultiply = bigDecimalBookPrice.multiply(bigDecimalBuyCount);

                totalPrice = totalPrice.add(bigDecimalMultiply);
            }
        }
        return totalPrice;
    }


    public Integer getTotalBookCount() {
        totalBookCount = 0;
        if(items != null && items.size() != 0){
            for (Map.Entry<Integer, CartItem> entry:
                    items.entrySet()) {
                CartItem book = entry.getValue();
                totalBookCount += book.getBuyCount();
            }
        }
        return totalBookCount;
    }

}
