package com.sriram.srirammart.dao.impl;

import com.sriram.srirammart.dao.CartDAO;
import com.sriram.srirammart.model.Cart;
import com.sriram.srirammart.model.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link CartDAO}.
 * There's no separate "cart" table — the cart_items rows for a user_id ARE the cart.
 */
public class CartDAOImpl implements CartDAO {

    private final DataSource dataSource;

    public CartDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Cart> findByBuyerId(long buyerId) {
        String sql = "SELECT product_id, quantity FROM cart_items WHERE user_id = ?";
        List<CartItem> items = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new CartItem(rs.getLong("product_id"), rs.getInt("quantity")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load cart", e);
        }

        return Optional.of(new Cart(0, buyerId, items));
    }

    /** If the product is already in the cart, quantities add up instead of duplicating the row. */
    @Override
    public void addItem(long buyerId, CartItem item) {
        String selectSql = "SELECT quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
        String updateSql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {

            Integer existingQty = null;
            try (PreparedStatement selectPs = conn.prepareStatement(selectSql)) {
                selectPs.setLong(1, buyerId);
                selectPs.setLong(2, item.getProductId());
                try (ResultSet rs = selectPs.executeQuery()) {
                    if (rs.next()) existingQty = rs.getInt("quantity");
                }
            }

            if (existingQty != null) {
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, existingQty + item.getQuantity());
                    updatePs.setLong(2, buyerId);
                    updatePs.setLong(3, item.getProductId());
                    updatePs.executeUpdate();
                }
            } else {
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setLong(1, buyerId);
                    insertPs.setLong(2, item.getProductId());
                    insertPs.setInt(3, item.getQuantity());
                    insertPs.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add item to cart", e);
        }
    }

    @Override
    public void updateItem(long buyerId, CartItem item) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setLong(2, buyerId);
            ps.setLong(3, item.getProductId());

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Product is not in the cart");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update cart item", e);
        }
    }

    @Override
    public void removeItem(long buyerId, long productId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, buyerId);
            ps.setLong(2, productId);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Product is not in the cart");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove cart item", e);
        }
    }

    @Override
    public void clearCart(long buyerId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, buyerId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear cart", e);
        }
    }
}