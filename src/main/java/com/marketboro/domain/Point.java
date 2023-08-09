package com.marketboro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Point {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_no")
    private Long pointNo;
    @Column(name = "point_value")
    private Long pointValue;
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "exp_date")
    private LocalDateTime expDate;
    @Column(name = "use_date")
    private LocalDateTime useDate;
    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
}