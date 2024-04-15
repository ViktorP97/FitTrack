//package com.vp.fittrack.security;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfig {
//
//  private final JWTAuthenticationFilter jwtAuthFilter;
//
//  private final AuthenticationProvider authenticationProvider;
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(authz -> authz
//            .requestMatchers("/home", "/h2-console/**", "/api/v1/auth/**", "/resources/**", "/register/**", "/login", "/verify/**", "/login", "/workout/zone/**")
//            .permitAll()
//            .requestMatchers(toH2Console()).permitAll()
//            .anyRequest().authenticated())
//        .sessionManagement((SessionManagementConfigurer<HttpSecurity> httpSecSessManConf) -> httpSecSessManConf
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .authenticationProvider(authenticationProvider)
//        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//        .headers((headers) ->
//            headers.frameOptions(withDefaults()).disable());
//    return http.build();
//  }
//
//  @Bean
//  WebSecurityCustomizer customizeWebSecurity() {
//    return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/templates/**", "/favicon.ico");
//  }
//}