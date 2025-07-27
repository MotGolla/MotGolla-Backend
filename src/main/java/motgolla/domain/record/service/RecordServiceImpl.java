package motgolla.domain.record.service;


import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import motgolla.domain.record.dto.response.RecordDetailResponse;
import motgolla.domain.record.mapper.RecordMapper;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;
import motgolla.infra.file.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordServiceImpl implements RecordService {


  private final RecordMapper recordMapper;
  private final FileService fileService;
  private final static String FOLDER = "record";
  private final static String BASEFILENAME = "product";


  @Transactional
  @Override
  public void registerRecord(RecordRegisterRequest recordRegisterRequest, Long memberId) {

    // 백화점 ID
    Long departmentStoreId = recordMapper.findDepartmentStoreByName(
        recordRegisterRequest.getDepartmentStore());

    // 백화점과 브랜드를 포함하는 테이블 ID
    Long brandDepartmentStoreId = recordMapper.findDepartmentStoreBrand(
        recordRegisterRequest.getBrandName(), departmentStoreId);

    // 상품 기록 등록
    recordMapper.insertRecord(recordRegisterRequest, memberId, brandDepartmentStoreId);

    // 상품 이미지 테이블에 이미지 정보 등록
    String uploadImgUrl = "";

    // 택 사진 저장
    if (recordRegisterRequest.getTagImg() != null && !recordRegisterRequest.getTagImg().isEmpty()) {
      uploadImgUrl = fileService.upload(recordRegisterRequest.getTagImg(), FOLDER,
          BASEFILENAME);
      recordMapper.insertRecordImage(uploadImgUrl, "TAG", recordRegisterRequest.getId(), memberId);
    }

    // 상풍 사진 저장
    List<MultipartFile> productImgs = recordRegisterRequest.getProductImgs();
    if (productImgs != null && !productImgs.isEmpty()) {
      for (MultipartFile multipartFile : productImgs) {
        if (multipartFile != null && !multipartFile.isEmpty()) {  // 파일 유효성 추가
          uploadImgUrl = fileService.upload(multipartFile, FOLDER, BASEFILENAME);
          recordMapper.insertRecordImage(uploadImgUrl, "RECORD", recordRegisterRequest.getId(),
              memberId);
        }
      }
    }


  }

  @Override
  public ProductToBarcodeScanDto confirmProductByBarcode(String barcodeNumber) {
    Optional<ProductToBarcodeScanDto> barcodeScanInfo = recordMapper.findBarcodeScanInfo(
        barcodeNumber);
    barcodeScanInfo.orElseThrow(() -> new BusinessException(
        ErrorCode.BARCODE_INFO_NOT_FOUND
    ));
    return barcodeScanInfo.get();
  }

  @Override
  public RecordDetailResponse getRecordDetail(Long recordId) {
    RecordDetailResponse record = recordMapper.findRecordMainById(recordId);
    if (record == null) {
      throw new BusinessException(
              ErrorCode.RECORD_NOT_FOUND
      );
    }

    // 1. 일반 이미지
    record.setImageUrls(recordMapper.findImageUrlsByRecordId(recordId));

    // 2. 태그 이미지 (1개만 존재)
    record.setTagImageUrl(recordMapper.findTagImageUrlByRecordId(recordId));

    // 3. 브랜드 위치 처리 (gender + location 로직 재사용)
    List<String> rawLocations = recordMapper.findBrandLocationInfoByRecordId(recordId);
    List<String> result = new ArrayList<>();

    if (rawLocations.size() == 1) {
      result.add(rawLocations.get(0).split("\\|")[1]);
    } else {
      for (String raw : rawLocations) {
        String[] parts = raw.split("\\|");
        String gender = parts[0];
        String location = parts[1];
        if (gender == null || gender.isBlank()) {
          result.add(location);
        } else {
          result.add(gender + " " + location);
        }
      }
    }

    record.setBrandLocationInfo(result);
    return record;
  }
}
