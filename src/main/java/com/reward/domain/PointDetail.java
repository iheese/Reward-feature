package com.reward.domain;

import com.reward.domain.enums.PointDetailStatus;
import com.reward.domain.enums.PointStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class PointDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_detail_no")
    private Long pointDetailNo;

    @Column(name = "point_detail_value")
    private Long pointDetailValue;

    @Column(name = "exp_date")
    private LocalDateTime expDate;

    @Column(name = "point_status")
    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_detail_status")
    private PointDetailStatus pointDetailStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_no")
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    public PointDetail updatePointDetailStatus() {
        this.pointDetailStatus = PointDetailStatus.USED;
        this.expDate = null;
        return this;
    }
}