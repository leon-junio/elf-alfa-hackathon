package com.elf.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Filtro de segurança para aplicar todos os niveis de segurança da API
     * Nesse local o backend vai dividir o portal externo do portal interno
     * Além de adicionar outras camadas de segurança como os filtros de JWT, sessão
     * e autenticação
     * 
     * @param http Um objeto do tipo HTTPSecurity interno do spring
     * @return SecurityFilterChain Retorna um filtro de segurança que vai ser
     *         automaticamente aplicado nas rotas internas
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(t -> t.requestMatchers(
                        "/api/auth/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/extern/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore((Filter) jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}