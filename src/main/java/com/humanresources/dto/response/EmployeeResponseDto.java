package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeResponseDto {
    private Long user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hireDate;
    private String birthDate;
    private int annualLeave;
    private Long company;
    private boolean isActive;
}
