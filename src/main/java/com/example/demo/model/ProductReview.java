package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "product_review")
@Getter
@Setter
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id")
    private int productReviewID;
    @Column(name = "content_rated")
    private String contentRated;
    @Column(name = "point_evaluation")
    private int point_evaluation;
    @Column(name = "content_seen")
    private String contentSeen;
    @Column(name = "status")
    private int status;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id",foreignKey = @ForeignKey(name = "fk_productreview_product"))
    private Products products;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",foreignKey = @ForeignKey(name = "fk_productreview_user"))
    private Users users;
}
