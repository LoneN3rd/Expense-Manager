package com.mercy.expensetracker.controller;

import com.mercy.expensetracker.model.Expense;
import com.mercy.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/expenses/all")
    public ResponseEntity<?> getAllExpenses(Pageable page){
        return new ResponseEntity<>(expenseService.getAllExpenses(page), HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        List<Expense> expenses = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expenses, !expenses.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping("/expenses/id")
    public ResponseEntity<?> queryExpenseById(@RequestParam Long id){
        List<Expense> expenses = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expenses, !expenses.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@Valid @RequestBody Expense expense){
        try {
            return new ResponseEntity<>(expenseService.createExpense(expense), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(@RequestBody Expense expense, @PathVariable Long id){
        try {
            return new ResponseEntity<>(expenseService.updateExpense(expense, id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/expenses/del/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpenseById(id);
            return new ResponseEntity<>("Expense with Id '"+ id +"' deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}