package com.reward.domain;

import com.reward.domain.common.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_Name")
    private String userName;

    @Column(name = "point_amount")
    private Long pointAmount;

    @OneToMany(mappedBy = "user")
    private List<Point> pointHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Point> pointDetailHistory = new ArrayList<>();

    public User addPoint(Long point) {
        this.pointAmount += point;
        return this;
    }

    public User minusPoint(Long point) {
        this.pointAmount -= point;
        return this;
    }
}
