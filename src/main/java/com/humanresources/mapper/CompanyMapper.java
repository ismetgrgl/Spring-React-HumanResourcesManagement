package com.humanresources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.humanresources.Vw.CompanyView;
import com.humanresources.dto.request.RegisterRequestDto;
import com.humanresources.dto.response.CompaniesForLeaveDTO;
import com.humanresources.entity.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toCompany(RegisterRequestDto dto);

    CompaniesForLeaveDTO companyViewToCompaniesForLeaveDTO(CompanyView companyView);

}
