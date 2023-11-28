package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productID;
    @Column(name = "name_product")
    private String nameProduct;
    @Column(name = "price")
    private double price;
    @Column(name = "avatar_image_product")
    private String avatarImageProduct;
    @Column(name = "title")
    private String title;
    @Column(name = "discount")
    private int discount;
    @Column(name = "status")
    private int status;
    @Column(name = "number_of_views")
    private int numberOfViews;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "product_type_id",referencedColumnName = "product_type_id",foreignKey = @ForeignKey(name = "fk_product_producttype"))
    private ProductType productType;
    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductImage> productImageSet;
    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductReview> productReviewSet;
    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderDetail> orderDetailSet;
    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CartItem> cartItemSet;
}
