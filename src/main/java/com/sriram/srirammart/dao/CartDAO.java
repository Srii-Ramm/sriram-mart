package com.sriram.srirammart.dao;

import com.sriram.srirammart.model.Cart;
import com.sriram.srirammart.model.CartItem;

import java.util.Optional;

public interface CartDAO {

    Optional<Cart> findByBuyerId(long buyerId);

    void addItem(long buyerId, CartItem item);

    void updateItem(long buyerId, CartItem item);

    void removeItem(long buyerId, long productId);

    void clearCart(long buyerId);
}