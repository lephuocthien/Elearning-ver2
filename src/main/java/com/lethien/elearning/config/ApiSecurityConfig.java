package com.lethien.elearning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.lethien.elearning.filter.JwtAuthenticateFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity

public class ApiSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/
    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowCredentials(false);
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        //http.cors(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .securityMatcher("**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**",
                                "/api/category",
                                "/api/role/get-all-not-admin",
                                "/api/user/file/load/**",
                                "/api/course/file/load/**",
                                "/api/course",
                                "/api/course/search-dto/**",
                                "/api/course/get-dto/**",
                                "/admin/login",
                                "/admin/perform_login",
                                "/admin/logout")
                        .permitAll()//Đối với link này thì không cần check thông tin đăng nhập
                        .requestMatchers("/api/role/**",
                                "/admin/home",
                                "/admin/role/**",
                                "/admin/user/**",
                                "/admin/course/**",
                                "/admin/category/**")
                        .hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/user/get-user-by-token",
                                "/api/user/update/**",
                                "/api/user/file/**")
                        .hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/user/get-user-by-teacher/**")
                        .hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                        .requestMatchers("/api/user/**")
                        .hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest()
                        .authenticated()
                );
        http
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .permitAll()
                );
        http
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .permitAll()
                );
        http.addFilter(new JwtAuthenticateFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), userDetailsService));

        /*http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );*/
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/assets/**");
    }
}
