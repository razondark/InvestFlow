package com.mvp.investservice.web.controller;

import com.mvp.investservice.domain.exception.InsufficientFundsException;
import com.mvp.investservice.service.BondService;
import com.mvp.investservice.service.StockService;
import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.bond.CouponResponse;
import com.mvp.investservice.web.dto.stock.DividendResponse;
import com.mvp.investservice.web.dto.stock.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/invest/bonds")
@RequiredArgsConstructor
@Tag(name="BondController", description="Работа с облигациями")
public class BondController {

    private final BondService bondService;

    @Operation(
            summary = "Получение информации об облигации",
            description = "Возвращает информацию об облигациях по: названию, figi, ticker, Uid"
    )
    @GetMapping
    public List<BondDto> getBondsByName(@Parameter(description = "Название/figi/ticker/Uid") @RequestParam(value = "name") String bondName) {
        return bondService.getBondsByName(bondName);
    }

    @Operation(
            summary = "Получение информации об облигациях",
            description = "Возвращает информацию об облигациях по номеру страницы и количеству на странице"
    )
    @GetMapping("/all")
    public List<BondDto> getAllBonds(@Parameter(description = "Номер страницы") @RequestParam(value = "page") Integer page,
                                     @Parameter(description = "Количество на странице") @RequestParam(value = "count") Integer count) {
        return bondService.getBonds(page, count);
    }

    @Operation(
            summary = "Получение информации об облигациях по сектору",
            description = "Возвращает информацию об облигациях по заданному сектору"
    )
    @GetMapping("/sector/{sectorName}")
    public List<BondDto> getBondsBySector(@Parameter(description = "Название сектора") @PathVariable String sectorName,
                                          @Parameter(description = "Искомое количество") @RequestParam(value = "count") Integer count) {
        return bondService.getBondsBySector(sectorName, count);
    }

    @Operation(
            summary = "Получение информации о купонах облигации",
            description = "Возвращает информацию о купонах облигации по figi"
    )
    @GetMapping("/coupons")
    public CouponResponse getCoupons(@Parameter(description = "figi облигации") @RequestParam String figi) {
        return bondService.getCoupons(figi);
    }

    @Operation(
            summary = "Покупка облигации",
            description = "Совершает покупку облигации на аккаунт API"
    )
    @PostMapping("/buy")
    public OrderResponse<BondDto> buyBond(@Parameter(description = "Сущность покупки актива") @RequestBody PurchaseDto purchaseDto) {
        try {
            return bondService.buyBond(purchaseDto);
        }
        catch (InsufficientFundsException ex) {
            throw new InsufficientFundsException(ex.getAssetInfo());
        }
    }

    @Operation(
            summary = "Продажа облигации",
            description = "Совершает продажу облигации с аккаунта API"
    )
    @PostMapping("/sale")
    public OrderResponse<BondDto> saleBond(@Parameter(description = "Сущность продажи актива") @RequestBody SaleDto saleDto) {
        return bondService.saleBond(saleDto);
    }
}
