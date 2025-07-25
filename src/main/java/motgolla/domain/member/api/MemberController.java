package motgolla.domain.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.dto.request.LoginRequest;
import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.response.MemberInfoResponse;
import motgolla.domain.member.dto.response.TokenResponse;
import motgolla.domain.member.service.MemberService;
import motgolla.domain.member.vo.Member;

@Slf4j
@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/develop")
	public ResponseEntity<TokenResponse> develop(@RequestBody SignUpRequest signUpRequest) {
		TokenResponse response = memberService.createDevelopAccount(signUpRequest);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@AuthenticationPrincipal Member member) {
		TokenResponse response = memberService.login(member);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		TokenResponse response = memberService.signUp(signUpRequest);
		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/resign")
	public ResponseEntity<String> resign(@AuthenticationPrincipal Member member){
		memberService.resign(member);
		return ResponseEntity.ok().body("success");
	}

	@PatchMapping("/logout")
	public ResponseEntity<String> logout(@AuthenticationPrincipal Member member){
		memberService.logout(member);
		return ResponseEntity.ok().body("success");
	}

	@GetMapping()
	public ResponseEntity<MemberInfoResponse> getMemberInfo(@AuthenticationPrincipal Member member){
		return ResponseEntity.ok().body(member.toDto());
	}

	@GetMapping("/test")
	public ResponseEntity<String> test(@AuthenticationPrincipal Member member){
		return ResponseEntity.ok().body(member.getName());
	}
}
