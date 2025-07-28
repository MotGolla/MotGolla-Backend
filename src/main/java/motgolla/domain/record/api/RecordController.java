package motgolla.domain.record.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.vo.Member;
import motgolla.domain.record.dto.request.MemoSummaryRequest;
import motgolla.domain.record.dto.request.RecordProductFilterRequest;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import motgolla.domain.record.dto.request.UpdateRecordStatusRequest;
import motgolla.domain.record.dto.response.MemoSummaryResponse;
import motgolla.domain.record.dto.response.RecordDatesResponse;
import motgolla.domain.record.dto.response.RecordDetailResponse;
import motgolla.domain.record.dto.response.RecordProductFilterListResponse;
import motgolla.domain.record.dto.response.RecordProductFilterResponse;
import motgolla.domain.record.dto.response.RecordRegisterResponse;
import motgolla.domain.record.service.MemoSummarizer;
import motgolla.domain.record.service.RecordService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Tag(name = "Record API", description = "쇼핑 기록 관련 API")
public class RecordController {

  private final RecordService recordService;
  private final MemoSummarizer memoSummarizer;

  @Operation(
      summary = "기록 등록 (안드로이드 전용) POSTMAN으로 진행하세요",
      description = "이미지 및 상품 정보를 포함한 기록을 등록합니다."
  )
  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RecordRegisterResponse> recordRegisterByPostman(
      @AuthenticationPrincipal Member member,
      @Parameter(description = "기록 등록 JSON 데이터")
      @ModelAttribute RecordRegisterRequest request
  ) {
    log.info("recordRegister() :: {}", request);
    recordService.registerRecord(request, member.getId());
    return ResponseEntity.ok().body(new RecordRegisterResponse(true, null));
  }


  @Operation(
      summary = "메모 요약",
      description = "STT 텍스트 메모를 AI 기반으로 요약합니다.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "STT 텍스트를 포함한 메모 요청",
          required = true,
          content = @Content(schema = @Schema(implementation = MemoSummaryRequest.class))
      )
  )
  @PostMapping("/memo-summary")
  public ResponseEntity<MemoSummaryResponse> summarizeMemo(
      @org.springframework.web.bind.annotation.RequestBody MemoSummaryRequest request) {

    log.info("summarizeMemo() :: {}", request);
    String summaryMemo = memoSummarizer.analyze(request.getSttMemo());
    return ResponseEntity.ok().body(new MemoSummaryResponse(summaryMemo));
  }


  @Operation(
      summary = "Record 상세 조회",
      description = "Record ID를 기반으로 상세 정보를 조회합니다."
  )
  @GetMapping("/{recordId}")
  public RecordDetailResponse getRecordDetail(
      @Parameter(description = "조회할 Record의 ID", example = "1")
      @PathVariable Long recordId) {
    return recordService.getRecordDetail(recordId);
  }


  @Operation(
      summary = "쇼핑 기록 상품 조회 (무한 스크롤)",
      description = "날짜, 카테고리, 커서 등을 기준으로 사용자의 쇼핑 기록 상품 목록을 조회합니다."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  @GetMapping("/products")
  public ResponseEntity<RecordProductFilterListResponse> getProducts(
      @Parameter(hidden = true)
      @AuthenticationPrincipal Member member,
      @ModelAttribute RecordProductFilterRequest request
  ) {
    log.info("getProducts() :: {} id :: {} ", request.toString(), member.getId());
    List<RecordProductFilterResponse> items = recordService.getProductsByCursor(member.getId(),
        request);
    // 다음 커서 계산 (가장 마지막 ID 기준)
    Long nextCursor = items.isEmpty() ? null : items.get(items.size() - 1).getRecordId();
    boolean hasNext = items.size() == request.getLimit();  // 딱 limit만큼 조회됐으면 다음 페이지가 있다고 판단
    log.info("getProducts() :: {} id :: {} ", items, member.getId());
    return ResponseEntity.ok(new RecordProductFilterListResponse(items, nextCursor, hasNext));
  }


  @Operation(
      summary = "기록 상태 변경",
      description = "사용자의 쇼핑 기록 상태를 COMPLETED로 변경합니다."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
      @ApiResponse(responseCode = "404", description = "기록 없음", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  @PatchMapping("/{recordId}/status")
  public ResponseEntity<Void> updateRecordStatus(
      @PathVariable Long recordId,
      @AuthenticationPrincipal Member member,
      @RequestBody UpdateRecordStatusRequest request
  ) {
    recordService.updateRecordStatus(member.getId(), recordId, request.getStatus());
    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "특정 년-월의 쇼핑 기록 날짜 조회",
      description = "사용자의 특정 년-월(예: 2025-07)에 해당하는 쇼핑 기록 날짜 목록을 조회합니다."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  @GetMapping("/dates")
  public ResponseEntity<RecordDatesResponse> findRecordDatesByYearMonth(
      @AuthenticationPrincipal Member member,
      @RequestParam String yearMonth
  ) {
    List<String> result = recordService.findRecordDatesByYearMonth(member.getId(), yearMonth);
    return ResponseEntity.ok(new RecordDatesResponse(result));
  }
}
