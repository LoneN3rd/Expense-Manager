package com.mercy.expensetracker.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class ErrorObject {
    private String message;
    private String error;
    private Integer status;
    private Date timestamp;
}
