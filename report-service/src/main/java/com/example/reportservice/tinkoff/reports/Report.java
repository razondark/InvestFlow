package com.example.reportservice.tinkoff.reports;

import com.example.reportservice.model.OperationResponse;
import com.example.reportservice.tinkoff.instruments.InfoInstruments;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.tinkoff.piapi.core.InvestApi;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Report {
    private static String filename;

    private static String sheetname;
    private static Workbook wb = new HSSFWorkbook();
    private static Sheet sheet;
    private static String[] headers = new String[12];

    public static void createSheet() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            wb.write(fos);
            System.out.println(sheetname + " report rady");
        }
    }

    public Report setTitle(String title1) {
        Row row = sheet.createRow(2);
        Cell cell = row.createCell(2);
        return this;
    }

    public Report setSheetname(String sheetname) {
        Report.sheetname = sheetname;
        sheet = wb.createSheet(sheetname);
        return this;
    }

    public Report setFilename(String filename) {
        Report.filename = filename;
        return this;
    }

    public Report setHeaders(String[] headers) {
        this.headers = headers;
        header(headers);
        return this;
    }

    private void header(String[] headers) {
        Row row = sheet.createRow(1);
        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }
    }

    public Report setDataFigi(ArrayList<String> positions) {
        int i = 2;
        var api = InvestApi.create("t.RFkt3RdHtuLiYL9hb4eidouNRYGOTkQI_Dv7sB4pE-nJsi3MqguKzX0s2pGl-BzqteLfaIbLD0Huh8rrwIqYgw");
        InfoInstruments infoInstruments = new InfoInstruments(api);
        for (String s : positions) {
            int j = 0;
            Row row = sheet.createRow(i++);

            Cell cellFigi = row.createCell(j++);
            cellFigi.setCellValue(s);

            Cell cellName = row.createCell(j++);
            cellName.setCellValue(infoInstruments.getName(s));

            Cell cellTicker = row.createCell(j++);
            cellTicker.setCellValue(infoInstruments.getTicker(s));

            Cell cellCurency = row.createCell(j++);
            cellCurency.setCellValue(infoInstruments.getCurency(s));

            Cell cellLot = row.createCell(j++);
            cellLot.setCellValue(infoInstruments.getLot(s));

            Cell cellPrice = row.createCell(j);
            cellPrice.setCellValue(infoInstruments.getPrice(s));
        }
        return this;
    }

    protected Report setOperations(OperationResponse operationResponse) {
        int rowPosition = 2;
        int operationIterator = 0;
        for (int i = 0; i < operationResponse.getOperations().size(); i++) {
            int cellPosition = 0;
            Row row = sheet.createRow(rowPosition++);

            Cell cellOperationId = row.createCell(cellPosition++);
            cellOperationId.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getOperationId().toString());

            Cell cellInstrumentType = row.createCell(cellPosition++);
            cellInstrumentType.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getInstrumentType());

            Cell cellAssertName = row.createCell(cellPosition++);
            cellAssertName.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getAssetName());

            Cell cellFigi = row.createCell(cellPosition++);
            cellFigi.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getFigi());

            Cell cellCurrency = row.createCell(cellPosition++);
            cellCurrency.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getCurrency());

            Cell cellOperationType = row.createCell(cellPosition++);
            cellOperationType.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getOperationType());

            Cell cellOperationState = row.createCell(cellPosition++);
            cellOperationState.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getOperationState());

            Cell cellQuantity = row.createCell(cellPosition++);
            cellQuantity.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getQuantity());

            Cell cellPayment = row.createCell(cellPosition++);
            cellPayment.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getPayment().toString());

            Cell cellInstrumentPrice = row.createCell(cellPosition++);
            cellInstrumentPrice.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getInstrumentPrice().toString());

            Cell cellLotPrice = row.createCell(cellPosition++);
            cellLotPrice.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getLotPrice().toString());

            Cell cellOperationDate = row.createCell(cellPosition);
            cellOperationDate.setCellValue(operationResponse.getOperations()
                    .get(operationIterator).getOperationDate().toString());

            operationIterator++;
        }
        return this;
    }
}
