package motgolla.domain.record.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/barcodes")
@RequiredArgsConstructor
@RestController
public class BarcodeController {

  private final RecordService recordService;

  @GetMapping("/{barcode}/product")
  public ResponseEntity<?> getProductByBarcode(@PathVariable String barcode) {
    log.info("getProductByBarcode() :: {}", barcode);
    ProductToBarcodeScanDto productToBarcodeScanDto = recordService.confirmProductByBarcode(
        barcode);
    return ResponseEntity.ok(productToBarcodeScanDto);
  }
}
