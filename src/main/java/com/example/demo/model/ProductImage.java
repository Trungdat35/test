package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "product_image")
@Getter
@Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private int producImageID;
    @Column(name = "titile")
    private String titile;
    @Column(name = "image_product")
    private String imageProduct;
    @Column(name = "status")
    private int status;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id",foreignKey =@ForeignKey(name = "fk_productimage_product"))
    private Products products;
}
