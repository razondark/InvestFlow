package com.mvp.investservice.web.mapper;

import com.mvp.investservice.util.MoneyParser;
import com.mvp.investservice.util.SectorBondUtil;
import com.mvp.investservice.web.dto.BrandLogoDto;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.bond.RiskLevel;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.mvp.investservice.util.MoneyParser.convertToBigDecimal;

@Component
public class BondMapper {

    public BondDto toDto(Bond bond, BigDecimal lastPrice) {
        BondDto bondDto = new BondDto();

        bondDto.setCurrency(bond.getCurrency());
        bondDto.setFigi(bond.getFigi());
        bondDto.setLots(bond.getLot());
        bondDto.setName(bond.getName());
        bondDto.setSector(SectorBondUtil.valueOfRussianName(bond.getSector()));
        bondDto.setCountryOfRiskName(bond.getCountryOfRiskName());
        bondDto.setTicker(bond.getTicker());
        bondDto.setMaturityDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(bond.getMaturityDate().getSeconds(), bond.getMaturityDate().getNanos()), ZoneId.systemDefault()));
        bondDto.setPlacementPrice(MoneyParser.moneyValueToBigDecimal(bond.getPlacementPrice()));
        bondDto.setRiskLevel(RiskLevel.valueOf(bond.getRiskLevel().name()));
        bondDto.setCouponQuantityPerYear(bond.getCouponQuantityPerYear());

        if (lastPrice != null) {
            bondDto.setPrice(lastPrice);
        }

        bondDto.setBrandLogo(new BrandLogoDto(bond.getBrand().getLogoName(),
                bond.getBrand().getLogoBaseColor(),
                bond.getBrand().getTextColor()));

        return bondDto;
    }

    public List<BondDto> toDto(List<Bond> bonds, List<BigDecimal> lastPrices) {
        List<BondDto> bondDtoList = new ArrayList<>(bonds.size());

        for (int i = 0; i < bonds.size(); i++) {
            bondDtoList.add(toDto(bonds.get(i), lastPrices.get(i)));
        }

        return bondDtoList;
    }
}
