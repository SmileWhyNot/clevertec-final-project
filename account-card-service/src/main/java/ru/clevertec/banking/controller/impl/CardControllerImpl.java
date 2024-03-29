package ru.clevertec.banking.controller.impl;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.controller.CardController;
import ru.clevertec.banking.dto.card.CardCurrencyResponse;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.service.CardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
@Slf4j
public class CardControllerImpl implements CardController {
    private final CardService service;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.equals(#request.customer_id())" +
            " and hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public CardResponse create(@RequestBody @Valid CardRequest request) {
        return service.save(request);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.equals(" +
            "@cardServiceImpl.findByCardNumber(#request.card_number()).customer_id()) and hasRole('ROLE_USER')")
    @PatchMapping
    public CardResponse update(@RequestBody @Valid CardRequestForUpdate request) {
        return service.update(request);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<CardResponse> getAll(@PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.equals(#uuid) " +
            "and hasRole('ROLE_USER')")
    @GetMapping("/by-customer-id/{uuid}")
    public List<CardResponse> findByCustomer(@PathVariable UUID uuid) {
        return service.findByCustomer(uuid);
    }

    @Override
    @PostAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.equals(returnObject.customer_id()) " +
            "and hasRole('ROLE_USER')")
    @GetMapping("/by-card-number/{cardNumber}")
    public CardCurrencyResponse findByCardNumber(@PathVariable String cardNumber) {
        return service.findByCardNumber(cardNumber);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_SUPER_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cardNumber}")
    public void delete(@PathVariable String cardNumber) {
        service.deleteByCardNumber(cardNumber);
    }
}
