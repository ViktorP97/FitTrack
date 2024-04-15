//package com.vp.fittrack.security;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import com.vp.fittrack.services.UserDataService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfiguration {
//
//  @Autowired
//  private UserDataService userDetailService;
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    return httpSecurity
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(registry -> {
//          registry.requestMatchers( "/register/**", "/home", "/h2-console/**").permitAll();
//          registry.requestMatchers("/admin/**").hasRole("ADMIN");
//          registry.requestMatchers("/user/**").hasRole("USER");
//          registry.requestMatchers(toH2Console()).permitAll();
//          registry.anyRequest().authenticated();
//        })
//        .formLogin(httpSecurityFormLoginConfigurer -> {
//          httpSecurityFormLoginConfigurer
//              .loginPage("/login")
//              .defaultSuccessUrl("/", true)
//              .permitAll();
//        })
//        .headers((headers) ->
//            headers.frameOptions(withDefaults()).disable())
//        .build();
//  }
//
//  @Bean
//  WebSecurityCustomizer customizeWebSecurity() {
//    return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/templates/**", "/favicon.ico");
//  }
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return userDetailService;
//  }
//
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setUserDetailsService(userDetailService);
//    provider.setPasswordEncoder(passwordEncoder());
//    return provider;
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}
