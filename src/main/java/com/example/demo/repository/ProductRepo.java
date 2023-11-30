package com.example.demo.repository;

import com.example.demo.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products,Integer> {
    @Query(value = "SELECT * FROM products WHERE product_type_id = :pdTID",nativeQuery = true)
    List<Products> getSPLienQuan(int pdTID);
    @Query(value = "select product_id from products where  number_of_views =(select  max(number_of_views) from products)",nativeQuery = true)
    int getSPNoiBat();
}
