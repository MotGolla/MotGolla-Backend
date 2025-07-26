package motgolla.domain.member.service;

import java.util.Map;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.dto.request.LoginRequest;
import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.response.MemberInfoResponse;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.domain.member.vo.Member;
import motgolla.global.auth.jwt.JwtProvider;
import motgolla.global.auth.login.OidcService;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;
import motgolla.global.util.HashUtil;
import motgolla.global.util.RedisUtil;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	private final JwtProvider jwtProvider;
	private final OidcService oidcService;
	private final RedisUtil redisUtil;

	@Override
	public TokenResponse createDevelopAccount(SignUpRequest signUpRequest) {
		signUpRequest.setOauthId(HashUtil.hash(signUpRequest.getOauthId()));
		memberMapper.insertMember(signUpRequest);

		return jwtProvider.provideAccessTokenAndRefreshToken(signUpRequest.getId());
	}

	@Override
	public TokenResponse signUp(SignUpRequest signUpRequest) {
		// 중복 검사
		String hashedOauthId = HashUtil.hash(signUpRequest.getOauthId());
		if(memberMapper.findByOauthId(hashedOauthId).isPresent()){
			throw new BusinessException(ErrorCode.DUPLICATED_MEMBER);
		}

		log.info("[회원 가입 요청]");
		Map<String, Object> claims = oidcService.verify(signUpRequest.getIdToken());
		String oauthId = (String) claims.get("sub");
		signUpRequest.setOauthId(HashUtil.hash(oauthId));

		memberMapper.insertMember(signUpRequest);
		Long memberId = signUpRequest.getId();
		return jwtProvider.provideAccessTokenAndRefreshToken(memberId);
	}

	@Override
	public void logout(Member member) {
		// 토큰 만료 처리
		redisUtil.delete(member.getId().toString());
	}

	@Override
	public void resign(Member member) {
		// 토큰 만료 처리
		redisUtil.delete(member.getId().toString());
		// 논리적 삭제
		memberMapper.updateIsDeleted(member.getId());
	}
}
