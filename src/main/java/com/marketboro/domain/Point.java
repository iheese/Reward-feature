package com.marketboro.domain;

import com.marketboro.domain.common.BaseTime;
import com.marketboro.domain.enums.PointStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Point extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_no")
    private Long pointNo;
    @Column(name = "point_value")
    private Long pointValue;
    @Column(name = "exp_date")
    private LocalDateTime expDate;
    @Column(name = "point_status")
    private PointStatus pointStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "point")
    private List<PointDetail> pointDetailList = new ArrayList<>();

    @PrePersist
    public void setExpDate() {
        this.expDate = getRegDate().plusYears(1);
    }
}