package com.lambdaschool.schools.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The application turns off any automatic web page generate done by Spring.
 * This is done to improve exception handling.
 * However, we do need some web page generate done for Swagger, so do that here.
 */
@Configuration
public class SwaggerWebMvc implements WebMvcConfigurer
{
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /**
         * Adds the Swagger web pages to Spring.
         * This still gives the following warning
         * <p>
         *     No mapping  for GET /
         *     No mapping for GET /csrf
         * </p>
         * All works though
         *
         * @param registry the place that holds the web pages for Spring
         */
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }
}
