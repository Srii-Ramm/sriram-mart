package com.sriram.srirammart.dao.impl;

import com.sriram.srirammart.dao.ReviewDAO;
import com.sriram.srirammart.model.Review;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation of {@link ReviewDAO}. */
public class ReviewDAOImpl implements ReviewDAO {

    private final DataSource dataSource;

    public ReviewDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Review review) {
        String sql = "INSERT INTO reviews (product_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, review.getProductId());
            ps.setLong(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) review.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save review", e);
        }
    }

    @Override
    public List<Review> findByProductId(long productId) {
        String sql = "SELECT id, product_id, user_id, rating, comment, created_at " +
                     "FROM reviews WHERE product_id = ? ORDER BY created_at DESC";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(new Review(
                            rs.getLong("id"),
                            rs.getLong("product_id"),
                            rs.getLong("user_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find reviews", e);
        }
        return reviews;
    }

    @Override
    public double findAverageRatingByProductId(long productId) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("avg_rating") : 0.0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to compute average rating", e);
        }
    }
}