package com.humanresources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.humanresources.Vw.MyExpenseView;
import com.humanresources.Vw.PendingExpenseView;
import com.humanresources.dto.response.ExpensesForApproveResponseDTO;
import com.humanresources.dto.response.MyExpensesResponseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    MyExpensesResponseDTO myExpenseViewToMyExpensesResponseDTO(MyExpenseView myExpenseView);
    ExpensesForApproveResponseDTO pendingExpenseViewToExpensesForApproveResponseDTO(PendingExpenseView pendingExpenseView);
}