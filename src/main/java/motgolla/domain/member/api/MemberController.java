package motgolla.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.dto.request.LoginRequest;
import motgolla.domain.member.dto.request.SocialLoginRequest;
import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.request.SocialSignUpRequest;
import motgolla.domain.member.dto.response.MemberInfoResponse;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.service.MemberService;
import motgolla.domain.member.vo.Member;

@Slf4j
@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 가입, 로그인, 정보 조회 등의 API")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/develop")
	@Operation(summary = "개발용 계정 생성", description = "개발용 회원가입 API (테스트용)")
	public ResponseEntity<TokenResponse> develop(@RequestBody SignUpRequest signUpRequest) {
		TokenResponse response = memberService.createDevelopAccount(signUpRequest);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/login/kakao")
	@Operation(summary = "카카오 로그인", description = "로그인 시도 후 회원 여부 판단")
	public String login(@RequestBody SocialLoginRequest socialLoginRequest) {
		return "success";
	}

	@PostMapping("/login")
	@Operation(summary = "일반 로그인", description = "로그인 시도 후 회원 여부 판단")
	public String login(@RequestBody LoginRequest loginRequest) {
		return "success";
	}

	@PostMapping("/sign-up")
	@Operation(summary = "회원가입", description = "일반 회원가입을 진행")
	public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		TokenResponse response = memberService.signUp(signUpRequest);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/sign-up/{oauthType}")
	@Operation(summary = "회원가입", description = "소셜 로그인 후 회원가입을 진행")
	public ResponseEntity<TokenResponse> socialSignUp(
		@RequestBody SocialSignUpRequest signUpRequest,
		@PathVariable("oauthType") String oauthType) {
		TokenResponse response = memberService.socialSignUp(signUpRequest, oauthType);
		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/resign")
	@Operation(summary = "회원 탈퇴", description = "회원 탈퇴 처리")
	public ResponseEntity<String> resign(@AuthenticationPrincipal Member member){
		memberService.resign(member);
		return ResponseEntity.ok().body("success");
	}

	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "로그아웃 처리")
	public ResponseEntity<String> logout(@AuthenticationPrincipal Member member){
		memberService.logout(member);
		return ResponseEntity.ok().body("success");
	}

	@GetMapping()
	@Operation(summary = "회원 정보 조회", description = "현재 로그인된 회원의 정보를 조회")
	public ResponseEntity<MemberInfoResponse> getMemberInfo(@AuthenticationPrincipal Member member){
		return ResponseEntity.ok().body(member.toDto());
	}
}
