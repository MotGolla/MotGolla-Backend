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
public class DepartmentStoreResponse {

        @Schema(description = "백화점 고유 ID", example = "1")
        private Long id;

        @Schema(description = "백화점 이름", example = "현대백화점 무역센터점")
        private String name;

        @Schema(description = "백화점 위도", example = "37.508615")
        private Double lat;

        @Schema(description = "백화점 경도", example = "127.059781")
        private Double lon;

        @Schema(description = "사용자 위치로부터의 거리 (단위: km)", example = "1.23")
        private Double distance;

}

