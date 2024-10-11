package com.humanresources.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.EmployeeRequestDto;
import com.humanresources.dto.response.EmployeeNameAndIdResponseDTO;
import com.humanresources.dto.response.EmployeeResponseDto;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.EmployeeService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> addEmployee(@RequestBody @Valid EmployeeRequestDto dto) {
        employeeService.addEmployee(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan eklendi").data(true).build());
    }

    @PutMapping("/update/{userId}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> updateEmployee(@PathVariable Long userId, @RequestBody @Valid EmployeeRequestDto dto) {
        employeeService.updateEmployee(userId, dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan güncellendi").data(true).build());
    }

    @DeleteMapping("/delete/{userId}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> deleteEmployee(@PathVariable Long userId) {
        employeeService.deleteEmployee(userId);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan silindi").data(true).build());
    }

    @GetMapping("/get-employees")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<List<EmployeeResponseDto>>> getEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeResponseDto>>builder()
                .code(200)
                .message("Çalışanlar listelendi")
                .data(employees)
                .build());
    }

    @GetMapping("/get-employees-by-company-manager/{companyManagerId}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<List<EmployeeResponseDto>>> getEmployeesByCompanyManagerId(@PathVariable Long companyManagerId) {
        List<EmployeeResponseDto> employees = employeeService.getEmployeesByCompanyManagerId(companyManagerId);
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeResponseDto>>builder()
                .code(200)
                .message("Çalışanlar listelendi")
                .data(employees)
                .build());
    }

    @PutMapping("/activate/{id}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> activateEmployee(@PathVariable Long id) {
        employeeService.activateEmployee(id);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan aktifleştirildi").data(true).build());
    }

    @PutMapping("/deactivate/{id}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> deactivateEmployee(@PathVariable Long id) {
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan pasifleştirildi").data(true).build());
    }


    @GetMapping("/get-employee-by-company-id/{companyId}")
    public ResponseEntity<ResponseDTO<List<EmployeeNameAndIdResponseDTO>>> getEmployeesByCompanyId(@PathVariable Long companyId) {
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeNameAndIdResponseDTO>>builder().code(200).message("Çalışan listesi gönderildi").data(employeeService.getEmployeesByCompanyId(companyId)).build());
    }
}

