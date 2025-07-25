package motgolla.domain.member.service;

import motgolla.domain.member.dto.request.LoginRequest;
import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.response.MemberInfoResponse;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.vo.Member;

public interface MemberService {
	TokenResponse createDevelopAccount(SignUpRequest signUpRequest);
	TokenResponse login(Member member);
	TokenResponse signUp(SignUpRequest signUpRequest);
	void logout(Member member);
	void resign(Member member);
}
