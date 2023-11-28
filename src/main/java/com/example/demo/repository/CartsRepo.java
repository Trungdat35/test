package com.example.demo.repository;

import com.example.demo.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepo extends JpaRepository<Carts,Integer> {
}
