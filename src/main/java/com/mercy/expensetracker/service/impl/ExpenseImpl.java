package com.mercy.expensetracker.service.impl;

import com.mercy.expensetracker.exception.ResourceException;
import com.mercy.expensetracker.model.Expense;
import com.mercy.expensetracker.repository.ExpenseRepository;
import com.mercy.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<Expense> getAllExpenses(Pageable page) {
        //return expenseRepository.findByIsDeleted(0, page).stream().toList(); // if returning a Page object
        return expenseRepository.findByIsDeleted(0, page);
    }

    @Override
    public List<Expense> getExpenseById(Long id) {
        List<Expense> expenseList = new ArrayList<>();
        Optional<Expense> expense = expenseRepository.findByIdAndIsDeleted(id, 0);
        if(expense.isPresent()){
            expenseList.add(expense.get());
            return expenseList;
        } else {
            throw new ResourceException("Expense with id '"+ id +"' not found");
        }
    }

    public Optional<Expense> getSingleExpenseById(Long id) {
        return expenseRepository.findByIdAndIsDeleted(id, 0);
    }

    @Override
    public Expense createExpense(Expense expense) {
        expense.setCreatedBy("mmutuku"); // replace with user e.g. logged in user
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpenseById(Long id) throws ResourceException {
        Optional<Expense> expense = this.getSingleExpenseById(id);
        if(expense.isPresent()){
           Expense expense_ =  expense.get();
           expense_.setIsDeleted(1);
        } else {
            throw new ResourceException("Expense with id '"+ id +"' not found");
        }
    }

    @Override
    public Expense updateExpense(Expense expense, Long id) throws ResourceException {
        Optional<Expense> existingExpense = this.getSingleExpenseById(id);
        if(existingExpense.isPresent()){
            Expense existingExpense_ = existingExpense.get();
            existingExpense_.setName(expense.getName() != null ? expense.getName() : existingExpense_.getName());
            existingExpense_.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense_.getDescription());
            existingExpense_.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense_.getAmount());
            existingExpense_.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense_.getCategory());
            return expenseRepository.save(existingExpense_);
        } else {
            throw new ResourceException("Expense with id '"+ id +"' not found");
        }
    }
}
