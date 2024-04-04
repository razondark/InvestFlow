package com.mvp.investservice.domain.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    @RequiredArgsConstructor
    @Data
    private class AssetInfo {
        public final BigDecimal assetPrice;
        public final BigDecimal balance;
    }

    @Getter
    private AssetInfo assetInfo;

    public InsufficientFundsException(AssetInfo assetInfo) {
        super("Недостаточно средств");
        this.assetInfo = assetInfo;
    }

    public InsufficientFundsException(BigDecimal assetPrice, BigDecimal balance) {
        super("Недостаточно средств");
        assetInfo = new AssetInfo(assetPrice, balance);
    }

    public InsufficientFundsException(String message, BigDecimal assetPrice, BigDecimal balance) {
        super(message);
        assetInfo = new AssetInfo(assetPrice, balance);
    }
}
