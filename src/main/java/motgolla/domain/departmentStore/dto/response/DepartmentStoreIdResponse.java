package motgolla.domain.departmentStore.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentStoreIdResponse {
    @Schema(description = "백화점 고유 ID", example = "1")
    private Long id;

    @Schema(description = "백화점 이름", example = "현대백화점 무역센터점")
    private String storeName;
}
