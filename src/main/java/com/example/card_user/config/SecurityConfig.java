package com.example.card_user.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    protected final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;
    @Autowired
    public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
         builder.jdbcAuthentication()
                 //.usersByUsernameQuery("select username auth as users where username = ?")
                 .dataSource(dataSource)
                 .passwordEncoder(passwordEncoder);
    }

//    @Autowired
//    public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()
//                .withUser("Johnny")
//                .password(passwordEncoder.encode("root"))
//                .roles("Admin").and()
//                .passwordEncoder(passwordEncoder);
//    }



    @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().build();
    }
}
