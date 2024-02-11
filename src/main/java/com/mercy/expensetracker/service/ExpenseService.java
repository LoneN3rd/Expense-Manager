package com.mercy.expensetracker.service;

import com.mercy.expensetracker.exception.ResourceException;
import com.mercy.expensetracker.model.Expense;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    List<Expense> getAllExpenses(Pageable page);

    List<Expense> getExpenseById(Long id);

    Optional<Expense> getSingleExpenseById(Long id);

    Expense createExpense(Expense expense);

    void deleteExpenseById(Long id) throws ResourceException;

    Expense updateExpense(Expense expense, Long id) throws ResourceException;
}