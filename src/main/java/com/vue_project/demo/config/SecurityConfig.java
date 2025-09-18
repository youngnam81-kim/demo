package com.vue_project.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.vue_project.demo.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	private final JwtTokenProvider jwtTokenProvider;
//
//	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
//		this.jwtTokenProvider = jwtTokenProvider;
//	}

	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable()) // CSRF 보호
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(
							"/",		//api 서버로만 사용할것이라면 /api/** 만 해주면 된다.
							"/index.html",          // 명시적 index.html 요청
	                        "/favicon.ico",         // 파비콘
	                        "/css/**",              // 모든 CSS 파일
	                        "/js/**",               // 모든 JavaScript 파일
	                        "/images/**",           // 모든 이미지 파일
	                        "/static/**",           // Spring Boot 기본 정적 리소스 경로 (static 폴더 하위)
	                        "/public/**",           // Spring Boot 기본 정적 리소스 경로 (public 폴더 하위)
	                        "/webjars/**",          // WebJars 사용 시
	                        "/error",               // 에러 페이지
	                        "/api/**",              // 백엔드 API (인증 불필요한 API 포함)
	                        "/login"                // 로그인 페이지 (폼 로그인 시)
	                        ).permitAll() 
					.anyRequest().authenticated() // 그 외 요청은 인증 필요
			).addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
                    UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // <-- 세션 STATELESS 확인
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}