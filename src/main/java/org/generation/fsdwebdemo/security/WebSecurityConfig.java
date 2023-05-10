package org.generation.fsdwebdemo.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //To use the dependency injection method to inject the DataSource interface to
    //WebSecurityConfig Class so that we can use the properties or methods from the
    //DataSource Object
    //Purpose is to make use of the details we put in the application-dev.properties to be
    //able to make a connection to our MySQL server and access our Schema
    @Autowired
    private DataSource dataSource;


    /*  https://www.tabnine.com/code/java/methods/org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer/authoritiesByUsernameQuery
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        //when a user is authenticated, Spring Security object will create a user session. (Backend)
        //Spring Security object will also be responsible to manage the session (e.g. timeout, error)
        //Spring Security object will need to end the user session if logout or timeout

        //In an sql query, we can get the information from the front-end through the ? symbol
        //Sending of the information from the front-end is through the thymeleaf

        //AuthenticationManagerBuilder provides a usersByUsernameQuery method
        //1) Query to get the username, password, enabled from our users table that matches the
        //      what the front-end send to us (admin)
        //2) usersByUsernameQuery method will check the password matches and check enabled is 1
        //3) authoritiesByUsernameQuery method - to retrieve the role of this user


        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }
    /*
    https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //after the Authentication has been done, and user is login in and session is active
        //we need to setup the security policy for the http pages that we are able access

        http
                .csrf().disable();          //@CrossOrigin

        http.formLogin().loginPage("/login");

        http.formLogin()
                .defaultSuccessUrl("/productForm", true);

        http.logout()
                .logoutSuccessUrl("/index");

        //which are the pages or resources that we allow users to access without login
        //which are the page(s) that needs to have an Admin Role before user can access
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/product", "/index","/item/**","/images/**","/js/**","/css/**",
                        "/productimages/**","/login", "/aboutus").permitAll()
                .requestMatchers("/productForm/**").hasRole("ADMIN")
        );
        return http.build();
    }
}

