package com.mvp.investservice.web.mapper;

import com.mvp.investservice.service.impl.StockServiceImpl;
import com.mvp.investservice.util.SectorStockUtil;
import com.mvp.investservice.web.dto.BrandLogoDto;
import com.mvp.investservice.web.dto.stock.StockDto;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockMapper {
    public StockDto toDto(Share stock, BigDecimal lastPrice) {
        StockDto stockDto = new StockDto();

        stockDto.setName(stock.getName());
        stockDto.setFigi(stock.getFigi());
        stockDto.setTicker(stock.getTicker());
        stockDto.setCountryOfRiskName(stock.getCountryOfRiskName());
        stockDto.setCurrency(stock.getCurrency());
        stockDto.setSector(SectorStockUtil.valueOfRussianName(stock.getSector()));
        stockDto.setLots((int) stock.getLot());

        if (lastPrice != null) {
            stockDto.setPrice(lastPrice);
        }

        stockDto.setBrandLogo(new BrandLogoDto(stock.getBrand().getLogoName(),
                                                stock.getBrand().getLogoBaseColor(),
                                                stock.getBrand().getTextColor()));

        return stockDto;
    }

    public List<StockDto> toDto(List<Share> stocks, List<BigDecimal> lastPrices) {
        List<StockDto> stockDtoList = new ArrayList<>(stocks.size());

        for (int i = 0; i < stocks.size(); i++) {
            stockDtoList.add(toDto(stocks.get(i), lastPrices.get(i)));
        }

        return stockDtoList;
    }
}
