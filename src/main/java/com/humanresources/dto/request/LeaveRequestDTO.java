package com.humanresources.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.LeaveType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LeaveRequestDTO {
    private String token;
    private LeaveType leaveType;
    private String description;
    private Long startDate;
    private Long endDate;
}
