package com.example.reportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication (exclude = { DataSourceAutoConfiguration.class })
public class ReportServiceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ReportServiceApplication.class, args);
	}

}
