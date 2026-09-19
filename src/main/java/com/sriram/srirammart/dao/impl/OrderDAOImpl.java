package com.sriram.srirammart.dao.impl;

import com.sriram.srirammart.dao.OrderDAO;
import com.sriram.srirammart.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static final String BASE_SELECT =
            "SELECT id, buyer_id, status, total_amount, created_at FROM orders";

    private final DataSource dataSource;

    public OrderDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO orders (buyer_id, status, total_amount) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, order.getBuyerId());
            ps.setString(2, order.getStatus().name());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) order.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save order", e);
        }
    }

    @Override
    public void saveItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, item.getOrderId());
            ps.setLong(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save order item", e);
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        String sql = BASE_SELECT + " WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find order by id", e);
        }
    }

    @Override
    public List<Order> findByBuyerId(long buyerId) {
        String sql = BASE_SELECT + " WHERE buyer_id = ? ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) orders.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find orders by buyer id", e);
        }
        return orders;
    }

    @Override
    public List<Order> findBySellerId(long sellerId) {
        String sql = "SELECT DISTINCT o.id, o.buyer_id, o.status, o.total_amount, o.created_at " +
                     "FROM orders o " +
                     "JOIN order_items oi ON oi.order_id = o.id " +
                     "JOIN products p ON p.id = oi.product_id " +
                     "WHERE p.seller_id = ? ORDER BY o.created_at DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) orders.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find orders by seller id", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        String sql = BASE_SELECT + " ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) orders.add(mapRow(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all orders", e);
        }
        return orders;
    }

    @Override
    public List<OrderItem> findItemsByOrderId(long orderId) {
        String sql = "SELECT id, order_id, product_id, quantity, unit_price FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getLong("id"));
                    item.setOrderId(rs.getLong("order_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("unit_price"));
                    items.add(item);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find order items", e);
        }
        return items;
    }

    @Override
    public void updateStatus(long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setLong(2, orderId);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("No order found with id " + orderId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update order status", e);
        }
    }

    /** Used by ReviewService to check F8 eligibility: has this buyer actually bought this product? */
    @Override
    public boolean existsPurchase(long buyerId, long productId) {
        String sql = "SELECT 1 FROM orders o JOIN order_items oi ON oi.order_id = o.id " +
                     "WHERE o.buyer_id = ? AND oi.product_id = ? " +
                     "AND o.status <> 'CANCELLED' AND o.status <> 'PENDING'";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, buyerId);
            ps.setLong(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check purchase history", e);
        }
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getLong("id"),
                rs.getLong("buyer_id"),
                rs.getBigDecimal("total_amount"),
                OrderStatus.valueOf(rs.getString("status"))
        );
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return order;
    }
}