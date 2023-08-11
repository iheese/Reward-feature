package com.marketboro.domain;

import com.marketboro.domain.common.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;
    @Column(name = "userName")
    private String userName;
    @Column(name = "point_amount")
    private Long pointAmount;
    @OneToMany(mappedBy = "user")
    private List<Point> pointHistory = new ArrayList<>();

    public User addPoint(Long point) {
        this.pointAmount += point;
        return this;
    }

    public User minusPoint(Long point) {
        this.pointAmount -= point;
        return this;
    }
}
