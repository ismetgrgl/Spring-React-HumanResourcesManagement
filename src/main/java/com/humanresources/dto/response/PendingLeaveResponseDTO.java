package com.humanresources.dto.response;

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
public class PendingLeaveResponseDTO {
    private Long leaveId;
    private Long employeeId;
    private String employeeName;
    private String employeeSurname;
    private LeaveType leaveType;
    private Long startDate;
    private Long endDate;
    private LeaveStatus leaveStatus;
    private String description;
}
