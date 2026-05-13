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
}