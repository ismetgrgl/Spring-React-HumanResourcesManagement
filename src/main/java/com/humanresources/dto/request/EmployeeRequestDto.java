package com.humanresources.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hireDate;
    private String birthDate;
    private Long company;
    private Integer annualLeave;
    private BigDecimal salary;
    private boolean isActive;
}