package com.example.reportservice.tinkoff.reports;

import ru.tinkoff.piapi.core.models.Positions;

import java.io.IOException;
import java.util.ArrayList;

public class StockReport extends Report{
    public StockReport(ArrayList<String> positions) throws IOException {
        super();
        this.setSheetname("Stocks")
                .setFilename("report.xls")
                .setHeaders(new String[]{"figi", "наименование", "тикер", "валюта", "лот", "шаг цены"})
                .setDataFigi(positions);
        Report.createSheet();
    }
}
