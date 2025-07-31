package motgolla.domain.member.service;

import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.request.SocialSignUpRequest;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.vo.Member;

public interface MemberService {
	TokenResponse createDevelopAccount(SignUpRequest signUpRequest);
	TokenResponse signUp(SignUpRequest signUpRequest);
	TokenResponse socialSignUp(SocialSignUpRequest signUpRequest, String memberType);
	void logout(Member member);
	void resign(Member member);
}
