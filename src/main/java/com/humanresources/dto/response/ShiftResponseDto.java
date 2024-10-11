package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftResponseDto {
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private List<EmployeeShiftDto> employees;
}
