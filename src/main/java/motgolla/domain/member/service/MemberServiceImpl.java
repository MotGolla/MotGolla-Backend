package motgolla.domain.member.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.dto.request.LoginRequest;
import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.domain.member.vo.Member;
import motgolla.global.auth.jwt.JwtProvider;
import motgolla.global.auth.login.OidcService;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	private final JwtProvider jwtProvider;
	private final OidcService oidcService;

	@Override
	public TokenResponse createDevelopAccount(SignUpRequest signUpRequest) {
		memberMapper.insertMember(signUpRequest);
		Member member = memberMapper.findById(signUpRequest.getId()).get();

		return jwtProvider.provideAccessTokenAndRefreshToken(member);
	}

	@Override
	public TokenResponse login(Member member) {
		return jwtProvider.provideAccessTokenAndRefreshToken(member);
	}

	@Override
	public TokenResponse signUp(SignUpRequest signUpRequest) {
		// 중복 검사
		if(memberMapper.findByOauthId(signUpRequest.getOauthId()).isPresent()){
			throw new BusinessException(ErrorCode.DUPLICATED_MEMBER);
		}
		Map<String, Object> claims = oidcService.verify(signUpRequest.getIdToken());
		String oauthId = (String) claims.get("sub");
		signUpRequest.setOauthId(oauthId);

		memberMapper.insertMember(signUpRequest);
		Long memberId = signUpRequest.getId();
		String accessToken = jwtProvider.createAccessToken(memberId);
		String refreshToken = jwtProvider.createRefreshToken(memberId);
		return new TokenResponse(accessToken, refreshToken);
	}

	@Override
	public void logout(Member member) {
		// 토큰 만료 처리
	}

	@Override
	public void resign(Member member) {
		// 논리적 삭제
	}
}
