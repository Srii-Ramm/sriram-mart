package com.sriram.srirammart.dao;

import com.sriram.srirammart.model.Order;
import com.sriram.srirammart.model.OrderItem;
import com.sriram.srirammart.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    void save(Order order);
    void saveItem(OrderItem item);
    Optional<Order> findById(long id);
    List<Order> findByBuyerId(long buyerId);
    List<Order> findBySellerId(long sellerId);
    List<Order> findAll();
    List<OrderItem> findItemsByOrderId(long orderId);
    void updateStatus(long orderId, OrderStatus status);
    boolean existsPurchase(long buyerId, long productId);
}