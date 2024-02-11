package com.mercy.expensetracker.service.impl;

import com.mercy.expensetracker.exception.ResourceException;
import com.mercy.expensetracker.model.Expense;
import com.mercy.expensetracker.repository.ExpenseRepository;
import com.mercy.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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
        //List<Expense> expenseList = new ArrayList<>();
        Optional<Expense> expense = expenseRepository.findByIdAndIsDeleted(id, 0);
        if(expense.isPresent()) {
            //expenseList.add(expense.stream().toList());
            return expense.stream().toList();
        } else {
            throw new ResourceException("Expense with id '" + id + "' not found");
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
            System.out.println("expense found "+ expense.get().getId());
            Expense expense_ =  expense.get();
            expense_.setIsDeleted(1);
            expenseRepository.save(expense_);
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

    @Override
    public List<Expense> getExpenseByName(String name, Pageable page) {
        return expenseRepository.findByIsDeletedAndNameContaining(0, name, page).toList();
    }

    @Override
    public List<Expense> getExpenseByDateCreated(String from, String to, Pageable page) {
        ZonedDateTime z_from = ZonedDateTime.of(from != null ? LocalDate.parse(from) : LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
        ZonedDateTime z_to = to != null ? ZonedDateTime.of(LocalDate.parse(to), LocalTime.MIDNIGHT, ZoneId.systemDefault()) : z_from;
        ZonedDateTime z_to_plus_one = z_to.plusDays(1);
        Date filter_from = Date.from(z_from.toInstant());
        Date filter_to = Date.from(z_to_plus_one.toInstant());
        System.out.println("Getting bills created on "+ filter_from +" to "+ filter_to);
        return expenseRepository.findByIsDeletedAndCreatedOnBetween(0, filter_from, filter_to, page).toList();
    }
}
