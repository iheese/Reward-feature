package com.marketboro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;
    @Column(name = "userName")
    private String userName;
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "point_amount")
    private Long pointAmount;
    @OneToMany(mappedBy = "user")
    private List<Point> pointHistory = new ArrayList<>();
}
