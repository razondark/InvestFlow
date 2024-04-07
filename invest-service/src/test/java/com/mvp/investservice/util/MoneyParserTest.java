package com.mvp.investservice.util;

import org.junit.jupiter.api.Test;
import ru.tinkoff.piapi.contract.v1.MoneyValue;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MoneyParserTest {

    @Test
    void moneyValueToBigDecimal_NullInput_ReturnsNull() {

        MoneyValue moneyValue = null;


        BigDecimal result = MoneyParser.moneyValueToBigDecimal(moneyValue);


        assertNull(result);
    }
}

