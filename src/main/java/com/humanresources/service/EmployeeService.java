package com.humanresources.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.humanresources.Vw.EmployeeView;
import com.humanresources.dto.request.EmployeeRequestDto;
import com.humanresources.dto.response.EmployeeNameAndIdResponseDTO;
import com.humanresources.dto.response.EmployeeResponseDto;
import com.humanresources.entity.Company;
import com.humanresources.entity.CompanyManager;
import com.humanresources.entity.Employee;
import com.humanresources.entity.User;
import com.humanresources.entity.enums.UserRole;
import com.humanresources.exception.ErrorType;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.mapper.EmployeeMapper;
import com.humanresources.repository.CompanyManagerRepository;
import com.humanresources.repository.CompanyRepository;
import com.humanresources.repository.EmployeeRepository;
import com.humanresources.repository.UserRepository;
import com.humanresources.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final EmployeeMapper employeeMapper;
    private final CompanyRepository companyRepository;
    private final CompanyManagerRepository companyManagerRepository;

    public void addEmployee(EmployeeRequestDto dto) {
        User user;

        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        } else {
            user = new User();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword()); // Parola şifreleme işlemi gerekecek
            user.setUserRole(UserRole.EMPLOYEE); // Çalışan rolü atanıyor
            user = userRepository.save(user);
        }

        Employee employee = EmployeeMapper.INSTANCE.toEmployee(dto);
        employee.setUser(user);
        employee.setSalary(dto.getSalary());
        employee.setActive(dto.isActive());
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long userId, EmployeeRequestDto dto) {
        // Önce ilgili User nesnesini buluyoruz.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        // Sonra bu User'a ait Employee kaydını buluyoruz.
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));

        // Employee'yi güncelliyoruz.
        employee.setHireDate(Long.valueOf(dto.getHireDate()));
        employee.setBirthDate(Long.valueOf(dto.getBirthDate()));
        employee.setAnnualLeave(dto.getAnnualLeave());
        employee.setActive(dto.isActive());
        employeeRepository.save(employee);
    }


    public void deleteEmployee(Long userId) {
        // userId'ye göre User'ı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        // Bulunan User'a göre Employee'yi bul
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));

        // Employee'yi sil®
        employeeRepository.delete(employee);

    }


    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> EmployeeResponseDto.builder()
                        .user(employee.getUser().getId())
                        .firstName(employee.getUser().getFirstName())
                        .lastName(employee.getUser().getLastName())
                        .email(employee.getUser().getEmail())
                        .hireDate(employee.getHireDate().toString())
                        .birthDate(employee.getBirthDate().toString())
                        .annualLeave(employee.getAnnualLeave())
                        .isActive(employee.isActive())
                        .company(employee.getCompany().getId())
                        .build())
                .collect(Collectors.toList());
    }


    public void activateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));
        employee.setActive(true);
        employeeRepository.save(employee);
    }

    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    public List<EmployeeNameAndIdResponseDTO> getEmployeesByCompanyId(Long companyId) {
        List<EmployeeView> employeeViewList = employeeRepository.findAllBycompanyId(companyId);
        if (employeeViewList.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND); //TODO Employee not found olacak!
        }
        List<EmployeeNameAndIdResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (EmployeeView employeeView : employeeViewList) {
            employeeResponseDTOList.add(employeeMapper.employeeViewToEmployeeNameAndIdResponseDTO(employeeView));
        }
        return employeeResponseDTOList;
    }

    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

    }

    public List<EmployeeResponseDto> getEmployeesByCompanyManagerId(Long userId) {
        // Önce userId'ye göre CompanyManager'ı bul
        CompanyManager companyManager = companyManagerRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Company Manager not found"));

        // CompanyManager'ın yönettiği tüm şirketleri bulun
        List<Long> companyIds = companyRepository.findAllByCompanyManager(companyManager)
                .stream()
                .map(Company::getId)
                .collect(Collectors.toList());

        // Bu şirketlere bağlı tüm çalışanları bulun
        List<Employee> employees = employeeRepository.findAllByCompanyIdIn(companyIds);

        return employees.stream()
                .map(employee -> EmployeeResponseDto.builder()
                        .user(employee.getUser().getId())
                        .firstName(employee.getUser().getFirstName())
                        .lastName(employee.getUser().getLastName())
                        .email(employee.getUser().getEmail())
                        .hireDate(employee.getHireDate().toString())
                        .birthDate(employee.getBirthDate().toString())
                        .annualLeave(employee.getAnnualLeave())
                        .isActive(employee.isActive())
                        .company(employee.getCompany().getId())
                        .build())
                .collect(Collectors.toList());
    }
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
    }
}

