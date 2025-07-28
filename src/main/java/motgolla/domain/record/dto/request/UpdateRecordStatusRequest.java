package motgolla.domain.record.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "상품 상태 변경 요청")
public class UpdateRecordStatusRequest {

  @Schema(description = "변경 완료 상태", example = "COMPLETED")
  private String status;

}