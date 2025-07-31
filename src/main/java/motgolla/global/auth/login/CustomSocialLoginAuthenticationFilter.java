package motgolla.global.auth.login;

import static motgolla.global.config.SecurityConfig.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.global.util.HashUtil;

@Slf4j
public class CustomSocialLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String HTTP_METHOD = "POST";
	private static final String CONTENT_TYPE = "application/json";
	private static final String OAUTH_ID_KEY = "oauthId";
	private static final String RE_SIGNUP = "reSignUp";
	private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher(SOCIAL_LOGIN_URL, HTTP_METHOD); // 로그인 요청

	private final ObjectMapper objectMapper;
	private final MemberMapper memberMapper;

	public CustomSocialLoginAuthenticationFilter(ObjectMapper objectMapper, MemberMapper memberMapper) {
		super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
		this.objectMapper = objectMapper;
		this.memberMapper = memberMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
		log.info("CustomSocialLoginAuthenticationFilter 진입");

		if(request.getContentType() == null || !request.getContentType().startsWith(CONTENT_TYPE)) {
			throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
		}

		String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
		Map<String, Object> loginDataMap = objectMapper.readValue(messageBody, Map.class);
		String oauthId = (String) loginDataMap.get(OAUTH_ID_KEY);

		Object reSignUpObj = loginDataMap.get(RE_SIGNUP);
		boolean isReSignUp = reSignUpObj != null && "true".equalsIgnoreCase(reSignUpObj.toString().trim());

		if (isReSignUp) {
			log.info("[UserNamePasswordAuthenticationFilter] Re-Sign up");
			memberMapper.updateIsDeletedFalse(HashUtil.hash(oauthId));
		}

		String password = HashUtil.hash(UUID.randomUUID().toString());
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(oauthId, password);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
