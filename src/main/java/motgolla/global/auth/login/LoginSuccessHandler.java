package motgolla.global.auth.login;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.domain.member.vo.Member;
import motgolla.global.auth.jwt.JwtProvider;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	private final MemberMapper memberMapper;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws
		ServletException, IOException {
		// access token, refresh token 발급
		Member member = (Member) authentication.getPrincipal();
		String accessToken = jwtProvider.createAccessToken(member.getId());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());

		// refresh token 업데이트
		//memberMapper.updateRefreshToken(member.getId(), refreshToken);

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(
			Map.of(
				"accessToken", accessToken,
				"refreshToken", refreshToken
			)
		));

		log.info("로그인에 성공하였습니다. 회원 id={}", member.getId());
	}
}
