package com.example.reportservice.tinkoff.reports;

import com.example.reportservice.model.OperationResponse;

import java.io.IOException;
public class OperationReport extends Report{
    public OperationReport (OperationResponse operationResponse) throws IOException {
        super();
        this.setSheetname("Operations")
                .setFilename("report.xls")
                .setHeaders(new String[]{"Id", "Тип инструмента", "Наименование", "figi", "Валюта", "Тип Операции",
                        "Операция", "Количество", "Оплачено", "Цена инструмента", "Цена Лота", "Дата"})
                .setOperations(operationResponse);
        Report.createSheet();
    }
}
