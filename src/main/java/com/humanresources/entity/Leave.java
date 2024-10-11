package com.humanresources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.LeaveStatus;
import com.humanresources.entity.enums.LeaveType;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee employee;
    private String description;
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    private Long startDate;
    private Long endDate;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LeaveStatus leaveStatus=LeaveStatus.PENDING;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
