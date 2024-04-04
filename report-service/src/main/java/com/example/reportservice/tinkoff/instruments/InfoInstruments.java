package com.example.reportservice.tinkoff.instruments;

import ru.tinkoff.piapi.core.InvestApi;

import static ru.tinkoff.piapi.core.utils.MapperUtils.quotationToBigDecimal;

public class InfoInstruments {
    private static InvestApi api = null;
    public InfoInstruments(InvestApi api) {
        this.api = api;
    }
    public static int getLot(String figi){
        return api.getInstrumentsService().getInstrumentByFigiSync(figi).getLot();
    }
    public static String getTicker(String figi) {
        return api.getInstrumentsService().getInstrumentByFigiSync(figi).getTicker();
    }
    public static String getPrice(String figi) {
        return quotationToBigDecimal(api.getMarketDataService()
                .getOrderBookSync(api.getInstrumentsService().getInstrumentByFigiSync(figi).getFigi(), 10)
                .getLastPrice()).toString();
    }
    public static String getCurency(String figi) {
        return api.getInstrumentsService().getInstrumentByFigiSync(figi).getCurrency();
    }
    public static String getName(String figi) {
        return api.getInstrumentsService().getInstrumentByFigiSync(figi).getName();
    }
}
