package com.mvp.investservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.mvp.investservice.service.props.InvestProperties;
import com.mvp.investservice.web.handler.RestTemplateErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {

    private final InvestProperties investProperties;

    @Bean
    public InvestApi investApi() {
        return InvestApi.createSandbox(investProperties.getToken());
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        //return new TransactionAwareCacheManagerProxy(cacheManager);
        return cacheManager;
    }

    @Bean
    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .recordStats();
    }
}
