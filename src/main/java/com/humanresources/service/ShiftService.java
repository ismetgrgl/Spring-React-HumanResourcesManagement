package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.humanresources.dto.request.ShiftRequestDto;
import com.humanresources.dto.response.EmployeeShiftDto;
import com.humanresources.dto.response.ShiftResponseDto;
import com.humanresources.entity.Employee;
import com.humanresources.entity.Shift;
import com.humanresources.repository.ShiftRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final EmployeeService employeeService;



    public void assignShifts(ShiftRequestDto dto) {
        Employee employee = employeeService.findEmployeeById(dto.getEmployeeId());
        Shift shift = Shift.builder()
                .employee(Collections.singletonList(employee))
                .startDate(dto.getStartDate().toString())
                .endDate(dto.getEndDate().toString())
                .startTime(dto.getStartTime().toString())
                .endTime(dto.getEndTime().toString())
                .build();
        shiftRepository.save(shift);
    }

    public List<ShiftResponseDto> getAllShifts() {
        List<Shift> allShifts = shiftRepository.findAll();

        return allShifts.stream()
                .map(shift -> ShiftResponseDto.builder()
                        .startDate(shift.getStartDate())
                        .endDate(shift.getEndDate())
                        .startTime(shift.getStartTime())
                        .endTime(shift.getEndTime())
                        .employees(shift.getEmployee().stream()
                                .map(emp -> new EmployeeShiftDto(emp.getId(), emp.getUser().getFirstName(), emp.getUser().getLastName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

    }
}
