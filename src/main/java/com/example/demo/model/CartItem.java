package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int cartItemID;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id",foreignKey = @ForeignKey(name = "fk_cartitem_product"))
    private Products products;
    @ManyToOne
    @JoinColumn(name = "carts_id",referencedColumnName = "carts_id",foreignKey = @ForeignKey(name = "fk_cartitem_carts"))
    private Carts carts;
}
