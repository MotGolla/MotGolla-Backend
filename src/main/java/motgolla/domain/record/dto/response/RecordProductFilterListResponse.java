package motgolla.domain.record.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "상품 필터 결과 리스트 Response")
public class RecordProductFilterListResponse {

  @Schema(description = "상품 필터 결과 리스트", example = "[{...}, {...}]")
  private List<RecordProductFilterResponse> items;

  @Schema(description = "다음 페이지 요청을 위한 커서 값", example = "41")
  private Long nextCursor;

  @Schema(description = "다음 페이지 존재 여부", example = "true")
  private boolean hasNext;
}