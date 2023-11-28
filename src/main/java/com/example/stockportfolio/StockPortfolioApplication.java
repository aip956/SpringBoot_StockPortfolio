package com.example.stockportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class StockPortfolioApplication {

    public static void main(String[] args) {
        // Use PORT provided in environment or default to 8080
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            // default to 8080 if PORT not set
            port = "8080";
        }
        // Set the port and start the application
        SpringApplication app = new SpringApplication(StockPortfolioApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        app.run(args);
        System.out.println("Now running!");
    }
}
