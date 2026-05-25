package com.mycompany.techstore.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() { return null; }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class }; // Trỏ tới file WebConfig của bạn
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // Ánh xạ mọi yêu cầu bắt đầu từ /
    }

    @Override
    protected void customizeRegistration(jakarta.servlet.ServletRegistration.Dynamic registration) {
        // Cấu hình upload file
        // Tham số: location, maxFileSize, maxRequestSize, fileSizeThreshold
        registration.setMultipartConfig(new jakarta.servlet.MultipartConfigElement("", 10485760, 20971520, 0));
    }
}