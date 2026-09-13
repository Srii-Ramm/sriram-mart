package com.sriram.srirammart.dao;

import com.sriram.srirammart.model.Review;

import java.util.List;

public interface ReviewDAO {
    void save(Review review);
    List<Review> findByProductId(long productId);
    double findAverageRatingByProductId(long productId);
}