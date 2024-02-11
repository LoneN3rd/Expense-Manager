package com.mercy.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "expenses")
@Table(name = "tbl_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_name")
    @NotBlank(message = "Expense name cannot be null")
    @Size(min = 3, message = "Expense name must be at least 3 characters")
    private String name;

    @NotBlank(message = "Description cannot be null")
    @Size(min = 3, message = "Expense description must be at least 3 characters")
    private String description;

    @Column(name = "expense_amount", scale = 2, precision = 10)
    @NotNull(message = "Expense amount cannot be null")
    private BigDecimal amount;

    @NotBlank(message = "Category cannot be null")
    private String category;

    //private String ref;

    private Integer isDeleted = 0;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private Date createdOn;

    @UpdateTimestamp
    private Date lastUpdatedOn;

    private String createdBy;

    private String lastUpdatedBy;
}
