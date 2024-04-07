package com.mvp.investservice.util;

import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.tinkoff.piapi.core.utils.MapperUtils.quotationToBigDecimal;

public class MoneyParser {

    public static BigDecimal moneyValueToBigDecimal(MoneyValue moneyValue) {
        if (moneyValue == null) {
            return null;
        }

        return mapUnitsAndNanos(moneyValue.getUnits(), moneyValue.getNano());
    }

    private static BigDecimal mapUnitsAndNanos(long units, int nano) {
        if (units == 0 && nano == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(units).add(BigDecimal.valueOf(nano, 9));
    }

    // переводит units и nano из Quotation в значение BigDecimal
    public static BigDecimal convertToBigDecimal(Quotation value) {
        BigDecimal nanoAsDecimal = new BigDecimal(value.getNano()).divide(new BigDecimal(1_000_000_000), 9, RoundingMode.HALF_UP);
        return new BigDecimal(value.getUnits()).add(nanoAsDecimal);
    }

    // переводит units и nano из MoneyValue в значение BigDecimal
    public static BigDecimal convertToBigDecimal(MoneyValue value) {
        BigDecimal nanoAsDecimal = new BigDecimal(value.getNano()).divide(new BigDecimal(1_000_000_000), 9, RoundingMode.HALF_UP);
        return new BigDecimal(value.getUnits()).add(nanoAsDecimal);
    }
}
