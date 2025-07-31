# 소셜 로그인 필터 흐름

## 1. 소셜 로그인 요청 흐름

```
[Android Client] 
    ↓ POST /api/member/login/kakao
[CustomSocialLoginAuthenticationFilter]
    ↓ attemptAuthentication()
[CustomAuthenticationProvider]
    ↓ authenticate()
[LoginService (UserDetailsService)]
    ↓ loadUserByUsername()
[LoginSuccessHandler]
    ↓ onAuthenticationSuccess()
[Android Client] ← JWT Tokens
```

## 2. 일반 로그인 요청 흐름

```
[Android Client]
    ↓ POST /api/member/login
[CustomJsonUsernameAuthenticationFilter]
    ↓ attemptAuthentication()
[GeneralAuthenticationProvider]
    ↓ authenticate() + PasswordEncoder.matches()
[GeneralLoginService (UserDetailsService)]
    ↓ loadUserByUsername()
[LoginSuccessHandler]
    ↓ onAuthenticationSuccess()
[Android Client] ← JWT Tokens
```

## 3. 필터 체인 순서

```
HTTP Request
    ↓
JwtAuthenticationExceptionHandler
    ↓
JwtAuthenticationProcessingFilter (JWT 검증)
    ↓
CustomSocialLoginAuthenticationFilter (/kakao)
    ↓
CustomJsonUsernameAuthenticationFilter (/login)
    ↓
Controller
```

## 4. 인증 실패 시

```
[Filter] → AuthenticationException
    ↓
[LoginFailureHandler]
    ↓ onAuthenticationFailure()
[Android Client] ← 401 Unauthorized
```

## 5. 핵심 컴포넌트 역할

### Filters
- **CustomSocialLoginAuthenticationFilter**: 소셜 로그인 요청 처리
- **CustomJsonUsernameAuthenticationFilter**: 일반 로그인 요청 처리
- **JwtAuthenticationProcessingFilter**: JWT 토큰 검증

### Providers
- **CustomAuthenticationProvider**: 소셜 로그인 인증 (비밀번호 검증 없음)
- **GeneralAuthenticationProvider**: 일반 로그인 인증 (PasswordEncoder 사용)

### Services
- **LoginService**: 소셜 로그인용 UserDetailsService
- **GeneralLoginService**: 일반 로그인용 UserDetailsService

### Handlers
- **LoginSuccessHandler**: 인증 성공 시 JWT 토큰 발급
- **LoginFailureHandler**: 인증 실패 시 에러 응답 