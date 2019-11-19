package com.excilys.cdb.webapp.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb"})
public class SpringWebConfig implements WebMvcConfigurer {

  // @Bean
  // public ViewResolver viewResolver() {
  // InternalResourceViewResolver irv = new InternalResourceViewResolver();
  // irv.setPrefix("/WEB-INF/views/");
  // irv.setSuffix(".jsp");
  // return irv;
  // }
  //
  // @Override
  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
  // registry
  // .addResourceHandler("/resources/**")
  // .addResourceLocations("/resources/");
  // }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("GET", "POST");
  }
}
