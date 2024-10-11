package com.humanresources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.humanresources.Vw.PendingLeaveView;
import com.humanresources.dto.response.PendingLeaveResponseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveMapper {
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);

    PendingLeaveResponseDTO pendingViewToPendingLeaveResponseDTO(PendingLeaveView pendingLeaveView);

}
