package com.mycompany.phonestore.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:database.properties", ignoreResourceNotFound = true) // Không báo lỗi nếu không có file trên Git/Render
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

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        String mailHost = System.getenv("MAIL_HOST");
        if (mailHost == null || mailHost.isEmpty()) {
            mailHost = env.getProperty("mail.host");
        }
        
        String mailPortStr = System.getenv("MAIL_PORT");
        if (mailPortStr == null || mailPortStr.isEmpty()) {
            mailPortStr = env.getProperty("mail.port", "587");
        }
        
        String mailUsername = System.getenv("MAIL_USERNAME");
        if (mailUsername == null || mailUsername.isEmpty()) {
            mailUsername = env.getProperty("mail.username");
        }
        
        String mailPassword = System.getenv("MAIL_PASSWORD");
        if (mailPassword == null || mailPassword.isEmpty()) {
            mailPassword = env.getProperty("mail.password");
        }
        
        if (mailHost != null) mailHost = mailHost.trim();
        if (mailPortStr != null) mailPortStr = mailPortStr.trim();
        if (mailUsername != null) mailUsername = mailUsername.trim();
        if (mailPassword != null) mailPassword = mailPassword.trim();

        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPortStr != null ? mailPortStr : "587"));
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
