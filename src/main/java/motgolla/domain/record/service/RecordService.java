package motgolla.domain.record.service;

import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.dto.request.RecordRegisterRequest;

public interface RecordService {

  void registerRecord(RecordRegisterRequest recordRegisterRequest ,Long memberId);

  ProductToBarcodeScanDto confirmProductByBarcode(String barcode);

}
