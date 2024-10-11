package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.ExpenseSaveDTO;
import com.humanresources.dto.request.UpdateExpenseRequestDTO;
import com.humanresources.dto.response.ExpensesForApproveResponseDTO;
import com.humanresources.dto.response.MyExpensesResponseDTO;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/save-expense")
    public ResponseEntity<ResponseDTO<Boolean>> saveExpense(@RequestBody ExpenseSaveDTO expenseSaveDTO){
        expenseService.saveExpense(expenseSaveDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Harcamanız onay için gönderildi").data(true).build());
    }


    @GetMapping("/get-my-expenses")
    public ResponseEntity<ResponseDTO<List<MyExpensesResponseDTO>>> getMyExpenses(@RequestHeader("Authorization") String token){
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(ResponseDTO.<List<MyExpensesResponseDTO>>builder().code(200).message("Harcamalar Gönderildi").data(expenseService.getMyExpenses(jwtToken)).build());
    }


    @GetMapping("/get-pending-expenses/{companyId}")
    public ResponseEntity<ResponseDTO<List<ExpensesForApproveResponseDTO>>> getPendingExpenses(@PathVariable Long companyId){
        return ResponseEntity.ok(ResponseDTO.<List<ExpensesForApproveResponseDTO>>builder().code(200).data(expenseService.getPendingExpenses(companyId)).message("Onay bekleyen harcalar gönderildi").build());
    }

    @PutMapping("/update-expense-status")
    public ResponseEntity<ResponseDTO<Boolean>> updateExpenseStatus(@RequestBody UpdateExpenseRequestDTO updateExpenseRequestDTO){
        expenseService.updateExpenseStatus(updateExpenseRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().message("expense status updated").code(200).data(true).build());
    }




}
