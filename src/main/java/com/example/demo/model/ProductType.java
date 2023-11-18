package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "product_type")
@Getter
@Setter
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private int productTypeID;
    @Column(name = "name_product_type")
    private String nameProductType;
    @Column(name = "image_product_type")
    private String imageProductType;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @OneToMany(mappedBy = "productType",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Products> productsSet;
}
