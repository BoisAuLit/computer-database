package com.excilys.cdb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.excilys.cdb")
public class WebConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

  // @Bean
  // public ViewResolver viewResolver() {
  // InternalResourceViewResolver irv = new InternalResourceViewResolver();
  // irv.setPrefix("/WEB-INF/views/");
  // irv.setSuffix(".jsp");
  // return irv;
  // }


  @Override
  protected void configure(HttpSecurity security) throws Exception {
    /**
     * Don't show the login page
     */
    security.httpBasic().disable();
  }
}
