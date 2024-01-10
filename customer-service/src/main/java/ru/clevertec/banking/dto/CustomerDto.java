package ru.clevertec.banking.dto;

import ru.clevertec.banking.entity.CustomerType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record CustomerDto(UUID id, CustomerType customerType, String unp, LocalDate registerDate, String email,
                               String phoneCode, String phoneNumber, String customerFullname) implements Serializable {
}
