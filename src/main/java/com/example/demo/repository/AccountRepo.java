package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByEmail(String email);
    @Query(value = "SELECT * FROM account as a where decentralization_id =:deID", nativeQuery = true)
    Account findByAccountByDeID (int deID);
}
