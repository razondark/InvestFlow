package com.mvp.investservice.service.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;

@Component
public class CacheService {
    @Cacheable("tradable-bonds-cache")
    public List<Bond> getTradableBondsSync(InvestApi investApi) {
        return investApi.getInstrumentsService().getTradableBondsSync();
    }

    @Cacheable("tradable-stocks-cache")
    public List<Share> getTradableStocksSync(InvestApi investApi) {
        return investApi.getInstrumentsService().getTradableSharesSync();
    }
}
