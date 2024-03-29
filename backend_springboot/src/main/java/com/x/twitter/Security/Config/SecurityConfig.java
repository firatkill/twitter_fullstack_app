package com.x.twitter.Security.Config;


import com.x.twitter.Security.Filter.CustomOPRFilter;
import com.x.twitter.Security.Filter.CustomUPFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final CustomUPFilter UPFFilter;
    private final AuthenticationManager authenticationManager;
    private final CustomOPRFilter OPRFFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(Customizer.withDefaults())
                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/auth/**").permitAll()
                )
                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/user/**").hasRole("USER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(UPFFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(OPRFFilter,UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
