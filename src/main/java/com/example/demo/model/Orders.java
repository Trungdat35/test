package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderID;
    @Column(name = "original_price")
    private double originalPrice;
    @Column(name = "actual_price")
    private double actualPrice;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @OneToMany(mappedBy = "orders",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderDetail> orderDetailSet;
    @ManyToOne
    @JoinColumn(name = "order_status_id",referencedColumnName = "order_status_id",foreignKey = @ForeignKey(name = "fk_order_status"))
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "payment_id",referencedColumnName = "payment_id",foreignKey = @ForeignKey(name = "fk_order_payment"))
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",foreignKey = @ForeignKey(name = "fk_order_user"))
    private Users users;
}
