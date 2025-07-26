package motgolla.domain.member.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
    @Schema(description = "회원 고유 ID", example = "1")
    Long id,

    @Schema(description = "회원 이름", example = "홍길동")
    String name,

    @Schema(description = "회원 생일 (YYYY-MM-DD)", example = "1990-01-01")
    String birthday,

    @Schema(description = "회원 성별 (M: 남성, F: 여성)", example = "M")
    String gender,

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    String profile,

    @Schema(description = "회원 가입일시", example = "2025-07-25T12:34:56")
    String createdAt
) {
}
