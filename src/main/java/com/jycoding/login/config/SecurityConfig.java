package com.jycoding.login.config;

import com.jycoding.login.config.jwt.JwtAuthorizationFilter;
import com.jycoding.login.model.UserRepository;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@Configuration // Bean 을 정의
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 활성화(실행 후), preAutorize 활성화(실행 전)
public class SecurityConfig{

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            // 시큐리티 관련 필터
            super.configure(builder);
        }
    }


        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            // h2 콘솔 사용을 위한 설정
            http.csrf((csrf) -> csrf
                            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                    .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

            // disable 하지 않으면 post, delete 요청을 막아버림
            http.csrf((csrf -> csrf.disable()));


            http.authorizeHttpRequests((authorize) -> {
                authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/user").hasAnyAuthority("USER")
                        .requestMatchers("/admin").hasAnyAuthority("ADMIN")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().permitAll();
            });

//            // 로그인 페이지는 loginForm 으로 설정, 기존 로그인 페이지가 아닌 직접 생성한 로그인 페이지로 보낸다.
            http.formLogin(form -> form.loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/"));

            // form 로그인 설정 해지
//            http.formLogin((formLogin)->formLogin.disable());

            // 세션 사용 안함
            http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            // cors 재설정
            http.cors((cors) -> configurationSource());

            // logout 설정
            http.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/loginForm"));

            // 로그인 필터 등록
//            http.addFilterAt(new L)

            // jwt 필터 등록
            http.with(new CustomSecurityFilterManager(), Customizer.withDefaults());


            return http.build();
        }

        // 권한 계층 설정 (어드민 > 유저 -> 어드민이 유저의 모든 권한을 상속받는다)
        @Bean
        static RoleHierarchy roleHierarchy() {
            RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
            hierarchy.setHierarchy("ADMIN > USER\n");
            return hierarchy;
        }

        // 메소드 수준의 보안 실행 -> 메소드가 호출될때 보안 검사를 수행
        @Bean
        static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
            DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
            expressionHandler.setRoleHierarchy(roleHierarchy);
            return expressionHandler;
        }

        // 비밀번호 인코딩
        @Bean
        BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public CorsConfigurationSource configurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
            configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
            configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
            configuration.addExposedHeader("Authorization");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

}

