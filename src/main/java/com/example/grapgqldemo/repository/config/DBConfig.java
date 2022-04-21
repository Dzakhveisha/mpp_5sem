package com.example.grapgqldemo.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@ComponentScan("com.example.grapgqldemo")
public class DBConfig {
    //@Value("#{environment.DB_PASSWORD}")
    @Value("mysqlPassword")
    private String dbPassword;

    //@Value("#{environment.DB_USER_NAME}")
    @Value("root")
    private String dbUserName;


    @Bean
    @Profile("default")
    public HikariDataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(dbUserName);
        config.setPassword(dbPassword);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(" jdbc:mysql://localhost:3306/task_list?serverTimezone=UTC ");
        config.addDataSourceProperty("serverName", "127.0.0.1");
        return new HikariDataSource(config);
    }
}