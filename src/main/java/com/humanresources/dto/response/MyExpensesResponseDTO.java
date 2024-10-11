package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.ExpenseStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyExpensesResponseDTO {
    private BigDecimal amount;
    private String description;
    private Long expenseDate;
    private ExpenseStatus expenseStatus;
}
