package com.sriram.srirammart.dao;

import com.sriram.srirammart.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    void save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll();

    void update(Product product);

    void delete(long id);
}