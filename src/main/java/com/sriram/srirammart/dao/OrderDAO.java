package com.sriram.srirammart.dao;

import com.sriram.srirammart.model.Order;
import com.sriram.srirammart.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    void save(Order order);

    void saveItem(OrderItem item);

    Optional<Order> findById(long id);

    List<Order> findByBuyerId(long buyerId);
}