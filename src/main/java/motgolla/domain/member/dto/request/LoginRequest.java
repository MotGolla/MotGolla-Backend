package motgolla.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    @Schema(description = "카카오 인증 후 받은 ID 토큰", example = "(아직 미사용이라 예시값 그대로 두면 됩니다)")
    String idToken,

    @Schema(description = "소셜 로그인에서 받은 고유 식별자", example = "kakao_123456")
    String oauthId
) {
}
