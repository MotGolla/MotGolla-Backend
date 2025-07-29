package motgolla.domain.record.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import motgolla.global.error.ErrorResponse;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "기록 등록 응답 Response")
public class RecordRegisterResponse {

  @Schema(description = "요청 성공 여부", example = "true")
  private boolean success;

  @Schema(description = "에러 정보 (요청 실패 시)")
  private ErrorResponse errorResponse;
}
