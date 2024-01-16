package ru.clevertec.banking.deposit.mapper;

import org.mapstruct.*;
import ru.clevertec.banking.deposit.model.domain.Deposit;
import ru.clevertec.banking.deposit.model.dto.message.DepositMessagePayload;
import ru.clevertec.banking.deposit.model.dto.request.CreateDepositRequest;
import ru.clevertec.banking.deposit.model.dto.request.UpdateDepositRequest;
import ru.clevertec.banking.deposit.model.dto.response.DepositResponse;

import java.util.Optional;

@Mapper
public interface DepositMapper {
    Deposit toDeposit(DepositMessagePayload messagePayload);

    Deposit updateDeposit(DepositMessagePayload messagePayload, @MappingTarget Deposit deposit);

    DepositResponse toDepositResponse(Deposit deposit);

    Deposit toDeposit(CreateDepositRequest createDepositRequest);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deposit updateDeposit(UpdateDepositRequest updateDepositRequest, @MappingTarget Deposit deposit);
}
