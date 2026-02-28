package com.ibrahimsolak.studentloginapp.security;

import com.ibrahimsolak.studentloginapp.security.filter.AuthenticationFilter;
import com.ibrahimsolak.studentloginapp.security.filter.ExceptionHandlerFilter;
import com.ibrahimsolak.studentloginapp.security.filter.JWTAuthorizationFilter;
import com.ibrahimsolak.studentloginapp.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                // 1. CORS Ayarını Aktif Et
                .cors(Customizer.withDefaults())
                // 2. CSRF'i Kapat (JWT kullandığın için güvenlidir)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 3. Kayıt ve Login Yollarına Herkese İzin Ver
                        .requestMatchers(HttpMethod.POST, SecurityConstants.STUDENT_REGISTER_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.TEACHER_REGISTER_PATH).permitAll()
                        .requestMatchers("/authenticate").permitAll()
                        // Diğer her şey için giriş istiyoruz
                        .anyRequest().authenticated()
                )
                // Filtre Zinciri (Sıralama Önemli!)
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();

    }

    // --- KRİTİK NOKTA: Angular'ın (4200) içeri girmesine izin veren yapılandırma ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Angular'ın çalıştığı adrese tam yetki veriyoruz
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        // JWT Token'ı Angular'ın okuyabilmesi için Authorization header'ını dışa açıyoruz
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
