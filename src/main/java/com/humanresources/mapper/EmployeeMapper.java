package com.humanresources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.humanresources.Vw.EmployeeView;
import com.humanresources.dto.request.EmployeeRequestDto;
import com.humanresources.dto.response.EmployeeNameAndIdResponseDTO;
import com.humanresources.entity.Company;
import com.humanresources.entity.Employee;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEmployee(EmployeeRequestDto dto);

    EmployeeNameAndIdResponseDTO employeeViewToEmployeeNameAndIdResponseDTO(EmployeeView employeeView);

    default Company map(Long value) {
        if (value == null) {
            return null;
        }
        Company company = new Company();
        company.setId(value);
        return company;
    }
}

