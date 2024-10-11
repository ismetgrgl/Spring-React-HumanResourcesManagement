package com.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.humanresources.Vw.MyExpenseView;
import com.humanresources.Vw.PendingExpenseView;
import com.humanresources.entity.Company;
import com.humanresources.entity.Employee;
import com.humanresources.entity.Expense;
import com.humanresources.entity.enums.ExpenseStatus;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("select new com.humanresources.Vw.MyExpenseView(e.amount,e.description,e.expenseDate,e.expenseStatus) from Expense  e where e.employee =?1")
    List<MyExpenseView> findAllMyExpenseViewByEmployee(Employee employee);


    @Query("select new com.humanresources.Vw.PendingExpenseView(e.id,e.amount,e.expenseDate,e.description,e.expenseStatus,e.employee.user.firstName,e.employee.user.lastName) from Expense e where e.company=?1 and e.expenseStatus=?2")
    List<PendingExpenseView> findAllPendingExpensesByCompany(Company company, ExpenseStatus expenseStatus);


}
