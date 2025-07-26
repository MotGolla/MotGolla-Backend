
package motgolla.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class SignUpRequest{
    @Schema(description = "회원 고유 ID (서버에서 내부적으로 사용)", example = "1")
    private Long id;

    @Schema(description = "소셜 로그인에서 받은 고유 식별자", example = "kakao_123456")
    @NotBlank(message = "oauthId는 필수입니다.")
    private String oauthId;

    @Schema(description = "카카오 인증 후 받은 ID 토큰", example = "eyJhbGciOiJSUzI1...")
    @NotBlank(message = "idToken은 필수입니다.")
    private String idToken;

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "성별 (F: 여성, M: 남성)", example = "M")
    @NotBlank(message = "성별은 필수입니다.")
    @Pattern(regexp = "^(F|M)$", message = "성별은 'F' 또는 'M'입니다.")
    private String gender;

    @Schema(description = "생년월일 (형식: YYYY-MM-DD)", example = "1990-01-01")
    @NotBlank(message = "생년월일은 필수입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일 형식은 YYYY-MM-DD이어야 합니다.")
    private String birthday;
}
