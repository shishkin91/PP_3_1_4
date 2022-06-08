package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    CustomUserDetailsService customUserDetailsService;
//    final SuccessUserHandler successUserHandler;
//    @Autowired
//    public void setCustomUserDetailsService (CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//
//    }
//
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }

//    /////////////////////////////////////

    CustomUserDetailsService customUserDetailsService;
    public final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService, SuccessUserHandler successUserHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}