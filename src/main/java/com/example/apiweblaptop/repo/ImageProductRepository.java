package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageProductRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByImageLink(String imageLink);
    void deleteAllByProduct(Product product);
}
