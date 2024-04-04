package com.mvp.investservice.config;

import com.mvp.investservice.service.props.InvestProperties;
import com.mvp.investservice.web.handler.RestTemplateErrorHandler;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class ApplicationConfigTest {

    @Mock
    private InvestProperties investProperties;

    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationConfig = new ApplicationConfig(investProperties);
    }

    @Test
    void testInvestApiBeanCreation() {
        InvestApi investApi = applicationConfig.investApi();
        assertNotNull(investApi);
    }

    @Test
    void testRestTemplateBeanCreation() {
        RestTemplate restTemplate = applicationConfig.restTemplate();
        assertNotNull(restTemplate);
        assertNotNull(restTemplate.getErrorHandler());
        assertNotNull(restTemplate.getErrorHandler() instanceof RestTemplateErrorHandler);
    }

    @Test
    void testCacheManagerBeanCreation() {
        Caffeine<Object, Object> caffeineCacheBuilder = applicationConfig.caffeineCacheBuilder();
        CacheManager cacheManager = applicationConfig.cacheManager(caffeineCacheBuilder);
        assertNotNull(cacheManager);
    }

    @Test
    void testCaffeineCacheBuilderBeanCreation() {
        Caffeine<Object, Object> caffeineCacheBuilder = applicationConfig.caffeineCacheBuilder();
        assertNotNull(caffeineCacheBuilder);

    }
}

