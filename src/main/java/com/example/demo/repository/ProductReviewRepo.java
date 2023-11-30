package com.example.demo.repository;

import com.example.demo.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview,Integer> {
    @Query(value = "SELECT *  FROM product_review  WHERE product_id = :pID", nativeQuery = true)
    List<ProductReview> getBinhLuanBySP(int pID);
}
