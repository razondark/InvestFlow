package com.mvp.investservice.web.controller;


import com.mvp.investservice.domain.exception.InsufficientFundsException;
import com.mvp.investservice.service.StockService;
import com.mvp.investservice.service.impl.StockServiceImpl;
import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.stock.DividendDto;
import com.mvp.investservice.web.dto.stock.DividendResponse;
import com.mvp.investservice.web.dto.stock.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/invest/stocks")
@RequiredArgsConstructor
@Tag(name="StockController", description="Работа с акциями")
public class StockController {

    private final StockService stockService;

    @Operation(
            summary = "Получение информации об акции",
            description = "Возвращает информацию об акциях по: названию, figi, ticker, Uid"
    )
    @GetMapping
    public List<StockDto> getStocksByName(@Parameter(description = "Название/figi/ticker/Uid") @RequestParam(value = "name") String stockName) {
        return stockService.getStocksByName(stockName);
    }

    @Operation(
            summary = "Получение информации об акциях",
            description = "Возвращает информацию об акциях по номеру страницы и количеству на странице"
    )
    @GetMapping("/all")
    public List<StockDto> getAllStocks(@Parameter(description = "Номер страницы") @RequestParam(value = "page") Integer page,
                                       @Parameter(description = "Количество на странице") @RequestParam(value = "count") Integer count) {
        return stockService.getStocks(page, count);
    }

    @Operation(
            summary = "Получение информации об акциях по сектору",
            description = "Возвращает информацию об акциях по заданному сектору"
    )
    @GetMapping("/sector/{sectorName}")
    public List<StockDto> getStocksBySector(@Parameter(description = "Название сектора") @PathVariable String sectorName,
                                            @Parameter(description = "Искомое количество") @RequestParam(value = "count") Integer count) {
        return stockService.getStocksBySector(sectorName, count);
    }

    @Operation(
            summary = "Получение информации о дивидендах акции",
            description = "Возвращает информацию о дивидендах акции по figi"
    )
    @GetMapping("/dividends")
    public DividendResponse getDividends(@Parameter(description = "figi акции") @RequestParam String figi) {
        return stockService.getDividends(figi);
    }

    // TODO: change try catch to ControllerAdvice
    @Operation(
            summary = "Покупка акции",
            description = "Совершает покупку акции на аккаунт API"
    )
    @PostMapping("/buy")
    public OrderResponse<StockDto> buyStock(@Parameter(description = "Сущность покупки актива") @RequestBody PurchaseDto purchaseDto) {
        try {
            return stockService.buyStock(purchaseDto);
        }
        catch (InsufficientFundsException ex) {
            throw new InsufficientFundsException(ex.getAssetInfo());
        }
    }

    @Operation(
            summary = "Продажа акции",
            description = "Совершает продажу акции с аккаунта API"
    )
    @PostMapping("/sale")
    public OrderResponse<StockDto> saleStock(@Parameter(description = "Сущность продажи актива") @RequestBody SaleDto saleDto) {
        return stockService.saleStock(saleDto);
    }
}