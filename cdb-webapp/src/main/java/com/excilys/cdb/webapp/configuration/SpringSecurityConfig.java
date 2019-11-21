package com.excilys.cdb.webapp.configuration;

// @Configuration
//// @EnableWebSecurity
// public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
// private static final String REALM = "MY_TEST_REALM";
//
// @Autowired
// public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
// auth.inMemoryAuthentication().withUser("bill").password("{noop}abc123").roles("ADMIN");
// auth.inMemoryAuthentication().withUser("tom").password("{noop}abc123").roles("USER");
// }
//
// @Override
// protected void configure(HttpSecurity http) throws Exception {
//
// http.csrf().disable()
// .authorizeRequests()
// .antMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER")
// .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
// .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
// }
//
// @Bean
// public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
// return new CustomBasicAuthenticationEntryPoint();
// }
//
// /* To allow Pre-flight [OPTIONS] request from browser */
// @Override
// public void configure(WebSecurity web) {
// web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
// }
// }
