package ru.clevertec.banking.advice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
}
