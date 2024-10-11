package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.humanresources.Vw.MyExpenseView;
import com.humanresources.Vw.PendingExpenseView;
import com.humanresources.dto.request.ExpenseSaveDTO;
import com.humanresources.dto.request.UpdateExpenseRequestDTO;
import com.humanresources.dto.response.ExpensesForApproveResponseDTO;
import com.humanresources.dto.response.MyExpensesResponseDTO;
import com.humanresources.entity.Company;
import com.humanresources.entity.Employee;
import com.humanresources.entity.Expense;
import com.humanresources.entity.enums.ExpenseStatus;
import com.humanresources.exception.ErrorType;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.mapper.ExpenseMapper;
import com.humanresources.repository.ExpenseRepository;
import com.humanresources.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final JwtTokenManager jwtTokenManager;
    private final EmployeeService employeeService;
    private final CompanyService companyService;


    public void saveExpense(ExpenseSaveDTO expenseSaveDTO) {
        Long userId = jwtTokenManager.getUserIdFromToken(expenseSaveDTO.getToken()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        Employee employee = employeeService.findEmployeeByUserId(userId);

        expenseRepository.save(Expense.builder()
                        .expenseDate(expenseSaveDTO.getExpenseDate())
                        .amount(expenseSaveDTO.getAmount())
                        .employee(employee)
                        .description(expenseSaveDTO.getDescription())
                        .company(employee.getCompany())
                .build());


    }

    public List<MyExpensesResponseDTO> getMyExpenses(String jwtToken) {
        Long userId = jwtTokenManager.getUserIdFromToken(jwtToken).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        Employee employee = employeeService.findEmployeeByUserId(userId);

        List<MyExpensesResponseDTO> myExpensesResponseDTOList = new ArrayList<>();

        List<MyExpenseView> myExpenseViewByEmployee = expenseRepository.findAllMyExpenseViewByEmployee(employee);
        if(myExpenseViewByEmployee.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND);
        }

        myExpenseViewByEmployee.forEach(expenseView -> {
            myExpensesResponseDTOList.add(ExpenseMapper.INSTANCE.myExpenseViewToMyExpensesResponseDTO(expenseView));
        });


        return myExpensesResponseDTOList;
    }

    public List<ExpensesForApproveResponseDTO> getPendingExpenses(Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        List<PendingExpenseView> allPendingExpensesByCompany = expenseRepository.findAllPendingExpensesByCompany(company,ExpenseStatus.PENDING);
        if(allPendingExpensesByCompany.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND);
        }
        List<ExpensesForApproveResponseDTO> allPendingExpensesResponseDTOList = new ArrayList<>();
        allPendingExpensesByCompany.forEach(expenseView -> {
            allPendingExpensesResponseDTOList.add(ExpenseMapper.INSTANCE.pendingExpenseViewToExpensesForApproveResponseDTO(expenseView));
        });
        return allPendingExpensesResponseDTOList;
    }

    public void updateExpenseStatus(UpdateExpenseRequestDTO updateExpenseRequestDTO) {
        Expense expense = expenseRepository.findById(updateExpenseRequestDTO.getExpenseId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND));
        if(updateExpenseRequestDTO.getIsApproved()){
            expense.setExpenseStatus(ExpenseStatus.APPROVED);
            Employee employee = employeeService.findEmployeeById(expense.getEmployee().getId());
            employee.setSalary(employee.getSalary().add(expense.getAmount()));
            employeeService.saveEmployee(employee);
        } else {
            expense.setExpenseStatus(ExpenseStatus.REJECTED);
        }
        expenseRepository.save(expense);
    }
}
