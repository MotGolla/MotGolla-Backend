package motgolla.domain.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Schema(description = "기록 등록 요청 Request")
public class RecordRegisterRequest {

  @Schema(description = "기록 ID", example = "빈값", hidden = true)
  private Long id;

  @Schema(description = "택 이미지", example = "tag.jpg")
  private MultipartFile tagImg;

  @Schema(description = "상품 이미지 목록", example = "[\"product1.jpg\", \"product2.jpg\"]")
  private List<MultipartFile> productImgs;

  @Schema(description = "백화점 브랜드 Id", example = "현대백화점 판교점")
  private Long department_store_brand_id;

  @Schema(description = "상품 식별번호", example = "1")
  private Long product_id;

  @Schema(description = "제품사이즈", example = "S")
  private String product_size;

  @Schema(description = "메모", example = "착화감이 좋음")
  private String note_summary;
}
