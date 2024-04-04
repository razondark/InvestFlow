package com.mvp.investservice.service;

import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.stock.DividendDto;
import com.mvp.investservice.web.dto.stock.DividendResponse;
import com.mvp.investservice.web.dto.stock.StockDto;

import java.util.List;

public interface StockService {

    List<StockDto> getStocksByName(String name);

    List<StockDto> getStocks(Integer page, Integer count);

    List<StockDto> getStocksBySector(String sectorName, Integer count);

    OrderResponse<StockDto> buyStock(PurchaseDto purchaseDto);

    OrderResponse<StockDto> saleStock(SaleDto saleDto);

    DividendResponse getDividends(String figi);
}
