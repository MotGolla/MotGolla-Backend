package motgolla.domain.record.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/barcodes")
@RequiredArgsConstructor
@Tag(name = "바코드", description = "바코드 기반 상품 조회 API")
public class BarcodeController {

  private final RecordService recordService;

  @Operation(
      summary = "바코드로 상품 조회",
      description = "입력된 바코드에 해당하는 상품 정보를 조회합니다.",
      parameters = {
          @Parameter(name = "barcode", description = "바코드 번호", example = "8800000000005")
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "조회 성공",
              content = @Content(schema = @Schema(implementation = ProductToBarcodeScanDto.class))
          ),
          @ApiResponse(responseCode = "400", description = "상품을 찾을 수 없음"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
      }
  )
  @GetMapping("/{barcode}/product")
  public ResponseEntity<?> getProductByBarcode(@PathVariable String barcode) {
    log.info("getProductByBarcode() :: {}", barcode);
    ProductToBarcodeScanDto productToBarcodeScanDto = recordService.confirmProductByBarcode(barcode);
    return ResponseEntity.ok(productToBarcodeScanDto);
  }
}
