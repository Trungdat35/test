package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailID;
    @Column(name = "price_total")
    private double priceTotal;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "order_id",foreignKey = @ForeignKey(name = "fk_odd_od"))
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id",foreignKey = @ForeignKey(name = "fk_odd_p"))
    private Products products;
}
