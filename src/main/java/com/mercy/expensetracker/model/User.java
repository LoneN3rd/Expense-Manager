package com.mercy.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "user")
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    //@NotBlank(message = "Password cannot be null")
    @JsonIgnore
    private String password;

    @CreationTimestamp
    private Date createdOn;

    @NotBlank(message = "CreatedBy cannot be null")
    private String createdBy;

    private String lastUpdatedBy;

    @UpdateTimestamp
    private Date lastUpdatedOn;

    private Integer isActive = 1;
}
