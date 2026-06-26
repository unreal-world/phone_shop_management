package com.mycompany.phonestore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties") // Đọc file thuộc tính database [cite: 453]
@ComponentScan(basePackages = "com.mycompany.phonestore") // Quét Service và DAO [cite: 430-435]
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        
        // Ưu tiên đọc từ biến môi trường (Render/Aiven), nếu không có sẽ lấy từ file database.properties
        String dbDriver = System.getenv("JDBC_DRIVER_CLASSNAME");
        if (dbDriver == null || dbDriver.isEmpty()) {
            dbDriver = env.getProperty("jdbc.driverClassName");
        }
        
        String dbUrl = System.getenv("JDBC_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = env.getProperty("jdbc.url");
        }
        
        String dbUsername = System.getenv("JDBC_USERNAME");
        if (dbUsername == null || dbUsername.isEmpty()) {
            dbUsername = env.getProperty("jdbc.username");
        }
        
        String dbPassword = System.getenv("JDBC_PASSWORD");
        if (dbPassword == null || dbPassword.isEmpty()) {
            dbPassword = env.getProperty("jdbc.password");
        }
        
        dataSource.setDriverClassName(dbDriver);
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        
        // Cấu hình HikariCP tối ưu cho môi trường deploy
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        dataSource.setIdleTimeout(30000);
        dataSource.setConnectionTimeout(20000);
        dataSource.setMaxLifetime(1800000);
        
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
