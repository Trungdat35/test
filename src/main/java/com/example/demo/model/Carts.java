package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carts_id")
    private int cartsID;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",foreignKey = @ForeignKey(name = "fk_carts_user"))
    private Users users;
    @OneToMany(mappedBy = "carts",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<CartItem> cartItemSet;
}
