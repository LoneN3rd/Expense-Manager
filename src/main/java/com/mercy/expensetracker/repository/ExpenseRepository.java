package com.mercy.expensetracker.repository;

import com.mercy.expensetracker.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByIsDeleted(int deleted, Pageable page);

    Optional<Expense> findByIdAndIsDeleted(Long id, int deleted);

    Page<Expense> findByIsDeletedAndNameContaining(int deleted, String name, Pageable page);

    Page<Expense> findByIsDeletedAndCreatedOnBetween(int deleted, Date from, Date to, Pageable page);
}
