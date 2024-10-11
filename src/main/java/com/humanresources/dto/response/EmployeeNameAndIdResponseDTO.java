package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeNameAndIdResponseDTO {
    private Long employeeId;
    private String employeeName;
    private String employeeSurname;
    private Integer annualLeave;
}
