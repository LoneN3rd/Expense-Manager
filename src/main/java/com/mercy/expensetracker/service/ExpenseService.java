package com.mercy.expensetracker.service;

import com.mercy.expensetracker.exception.ResourceNotFoundException;
import com.mercy.expensetracker.model.Expense;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    List<Expense> getAllExpenses(Pageable page);

    List<Expense> getExpenseById(Long id);

    Optional<Expense> getSingleExpenseById(Long id);

    Expense createExpense(Expense expense);

    void deleteExpenseById(Long id) throws ResourceNotFoundException;

    Expense updateExpense(Expense expense, Long id) throws ResourceNotFoundException;

    List<Expense> getExpenseByName(String name, Pageable page);

    List<Expense> getExpenseByDateCreated(LocalDate from, LocalDate to, Pageable page);
}