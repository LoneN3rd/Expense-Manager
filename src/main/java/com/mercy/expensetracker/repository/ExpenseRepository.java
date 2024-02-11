package com.mercy.expensetracker.repository;

import com.mercy.expensetracker.model.Expense;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByIsDeleted(int deleted, Pageable page);

    Optional<Expense> findByIdAndIsDeleted(Long id, int deleted);
}
