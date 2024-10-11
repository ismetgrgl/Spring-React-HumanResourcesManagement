package com.humanresources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Company company;

    private Long hireDate;
    private Long birthDate;
    private Integer annualLeave;
    private BigDecimal salary;
    private boolean isActive;

    @Builder.Default
    private Long createAt=System.currentTimeMillis();

    private Long updateAt;
}
