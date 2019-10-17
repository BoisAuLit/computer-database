package com.excilys.cdb.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.excilys.cdb")
public class BeansConfiguration implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    // AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    // ctx.setAllowCircularReferences(true);
    // ctx.register(BeansConfiguration.class);
    // ctx.setServletContext(servletContext);
    //
    // ServletRegistration.Dynamic servlet =
    // servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
    // servlet.setLoadOnStartup(1);
    // servlet.addMapping("/");

  }
}
