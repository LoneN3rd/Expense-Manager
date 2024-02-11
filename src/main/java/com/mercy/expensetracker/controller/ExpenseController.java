package com.mercy.expensetracker.controller;

import com.mercy.expensetracker.model.Expense;
import com.mercy.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses(Pageable page){
        return new ResponseEntity<>(expenseService.getAllExpenses(page), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        List<Expense> expenses = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expenses, !expenses.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id")
    public ResponseEntity<?> queryExpenseById(@RequestParam Long id){
        List<Expense> expenses = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expenses, !expenses.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> queryExpenseByName(@PathVariable String name, Pageable page){
        return new ResponseEntity<>(expenseService.getExpenseByName(name, page), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<?> queryExpenseByDate(@RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to, Pageable page){
        return new ResponseEntity<>(expenseService.getExpenseByDateCreated(from, to, page), HttpStatus.OK);
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

    @PutMapping("/del/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpenseById(id);
            return new ResponseEntity<>("Expense with Id '"+ id +"' deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
