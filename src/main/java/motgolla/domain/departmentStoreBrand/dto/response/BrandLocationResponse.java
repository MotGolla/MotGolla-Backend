package motgolla.domain.departmentStoreBrand.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandLocationResponse {
    @Schema(description = "백화점 매장 위치", example = "남성 4F")
    private String location;
    @Schema(description = "백화점-브랜드 조합 ID", example = "1")
    private Long departmentStoreBrandId;

}
