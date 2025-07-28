package motgolla.domain.record.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "위치 라벨 + 지도 링크")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreLocationInfo {
    @Schema(description = "성별 + 위치 라벨", example = "남성 4F")
    private String brandLocationInfo;
    @Schema(description = "지도 링크", example = "https://place.map.kakao.com/1795965148")
    private String storeMapLink;
}

