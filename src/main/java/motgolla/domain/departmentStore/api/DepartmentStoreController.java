package motgolla.domain.departmentStore.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreIdResponse;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;
import motgolla.domain.departmentStore.service.DepartmentStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/department-store")
@RequiredArgsConstructor
@Tag(name = "백화점 API", description = "백화점 관련 API")
public class DepartmentStoreController {

	private final DepartmentStoreService departmentStoreService;

	@GetMapping("/nearest")
	@Operation(summary = "가까운 위치 백화점 조회", description = "사용자 기준 가까운 백화점 조회")
	public ResponseEntity<DepartmentStoreResponse> getNearestStore(
			@RequestParam double lat,
			@RequestParam double lon
	){
		DepartmentStoreResponse response = departmentStoreService.findNearestStore(lat, lon);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/list")
	@Operation(summary = "백화점 목록 조회", description = "전체 백화점 ID와 지점명 조회")
	public ResponseEntity<List<DepartmentStoreIdResponse>> getStoreList() {
		return ResponseEntity.ok(departmentStoreService.getAllStores());
	}
}
