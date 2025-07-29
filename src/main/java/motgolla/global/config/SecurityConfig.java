package motgolla.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.global.auth.jwt.JwtAuthenticationExceptionHandler;
import motgolla.global.auth.jwt.JwtAuthenticationProcessingFilter;
import motgolla.global.auth.jwt.JwtProvider;
import motgolla.global.auth.login.CustomAuthenticationProvider;
import motgolla.global.auth.login.CustomJsonUsernamePasswordAuthenticationFilter;
import motgolla.global.auth.login.LoginFailureHandler;
import motgolla.global.auth.login.LoginService;
import motgolla.global.auth.login.LoginSuccessHandler;
import motgolla.global.util.RedisUtil;

@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public static final String URL_PREFIX = "/api";
    public static final String LOGIN_URL = URL_PREFIX + "/member/login";
    public static final String SIGNUP_URL = URL_PREFIX + "/member/sign-up";
    public static final String STATIC_RESOURCE = "/css/**";

    private final LoginService loginService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final MemberMapper memberMapper;
    private final RedisUtil redisUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .formLogin(form -> form.disable()) // 기본 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // HTTP Basic 인증 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책을 STATELESS로 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                LOGIN_URL,
                                SIGNUP_URL,
                                STATIC_RESOURCE,
                                URL_PREFIX + "/member/develop",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()); // 나머지 모든 경로 인증 필요

        // LogoutFilter -> JwtAuthenticationExceptionHandler -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationExceptionHandler(), JwtAuthenticationProcessingFilter.class);
        //http.addFilterBefore(new LogginFilter(), LogoutFilter.class);
        return http.build();
    }

    /**
     * AuthenticationManager 등록
     * 커스텀한 CustomAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(loginService);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper, memberMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtProvider, memberMapper);
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtProvider, memberMapper, redisUtil);
    }

    @Bean
    public JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler() {
        return new JwtAuthenticationExceptionHandler();
    }

}
