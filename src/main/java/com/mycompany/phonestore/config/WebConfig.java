package com.mycompany.phonestore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@PropertySource("classpath:database.properties") // Đọc trực tiếp file cấu hình từ resources [cite: 453-454]
@ComponentScan(basePackages = "com.mycompany.phonestore")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env; // Đối tượng env giúp truy xuất dữ liệu từ file properties an toàn 

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
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
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public org.springframework.web.multipart.support.StandardServletMultipartResolver multipartResolver() {
        return new org.springframework.web.multipart.support.StandardServletMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }
}
