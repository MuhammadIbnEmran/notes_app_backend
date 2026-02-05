package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DbConfig {

    @Value("${db_url}")
    private String dbUrl;

    @Value("${db_user}")
    private String dbUser;

    @Value("${db_pass}")
    private String dbPass;

    @Bean
    public Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(dbUrl, connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}
