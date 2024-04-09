package ua.reed.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.reed.urlshortener.controller.interceptor.RequestCountInterceptor;
import ua.reed.urlshortener.service.ClickStatsService;
import ua.reed.urlshortener.service.URLClickStatsService;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public ClickStatsService clickStatsService() {
        return new URLClickStatsService();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestCountInterceptor(clickStatsService()));
    }
}
