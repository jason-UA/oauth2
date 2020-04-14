package com.example.oauth2.support;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.AttributeConverter;

@Slf4j
public class MoneyConverter  implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmountMinorLong();
    }

    @Override
    public Money convertToEntityAttribute(Long aLong) {
        return Money.ofMinor(CurrencyUnit.of("CNY"), aLong);
    }
}
