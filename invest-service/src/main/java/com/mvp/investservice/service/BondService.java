package com.mvp.investservice.service;

import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.bond.CouponDto;
import com.mvp.investservice.web.dto.bond.CouponResponse;
import com.mvp.investservice.web.dto.stock.DividendDto;

import java.util.List;

public interface BondService {
    List<BondDto> getBondsByName(String name);

    List<BondDto> getBonds(Integer page, Integer count);

    List<BondDto> getBondsBySector(String sectorName, Integer count);

    OrderResponse<BondDto> buyBond(PurchaseDto purchaseDto);

    OrderResponse<BondDto> saleBond(SaleDto purchaseDto);

    CouponResponse getCoupons(String figi);
}
