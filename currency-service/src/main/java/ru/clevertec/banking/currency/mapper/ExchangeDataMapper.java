package ru.clevertec.banking.currency.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.banking.currency.model.domain.ExchangeData;
import ru.clevertec.banking.currency.model.dto.message.CurrencyRateMessagePayload;
import ru.clevertec.banking.currency.model.dto.response.ExchangeRateResponse;

@Mapper
public interface ExchangeDataMapper {

    ExchangeData toExchangeData(CurrencyRateMessagePayload messagePayload);
    ExchangeRateResponse toExchangeResponse(ExchangeData exchangeData);


    ExchangeData updateExchangeData(CurrencyRateMessagePayload messagePayload, @MappingTarget ExchangeData exchangeData);
}
