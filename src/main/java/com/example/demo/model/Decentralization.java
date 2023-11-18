package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "decentralization")
@Getter
@Setter
public class Decentralization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "decentralization_id")
    private int decentralizationID;
    @Column(name = "authority_name")
    private String authorityName;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @OneToMany(mappedBy = "decentralization",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Account> accountSet;
}
