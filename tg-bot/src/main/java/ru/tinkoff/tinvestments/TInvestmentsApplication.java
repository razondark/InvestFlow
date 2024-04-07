package ru.tinkoff.tinvestments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TInvestmentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TInvestmentsApplication.class, args);
    }
}
