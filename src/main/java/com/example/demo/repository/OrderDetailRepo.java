package com.example.demo.repository;

import com.example.demo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail,Integer> {
    @Query(value = "DELETE FROM order_detail WHERE order_id = :orderid",nativeQuery = true)
    void huyChiTietOrder(int orderid);
    @Query(value = "select count(*) from order_detail where product_id = :productid",nativeQuery = true)
    int luotMua(int productid);
}
