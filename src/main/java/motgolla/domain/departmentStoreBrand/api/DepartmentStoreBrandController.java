package motgolla.domain.departmentStoreBrand.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.departmentStoreBrand.service.DepartmentStoreBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department-store-brand")
@Tag(name = "백화점 API", description = "백화점 관련 API")
public class DepartmentStoreBrandController {

    private final DepartmentStoreBrandService departmentStoreBrandService;

    @GetMapping("/locations")
    public ResponseEntity<List<String>> getBrandLocations(
            @RequestParam("departmentStoreId") Long departmentStoreId,
            @RequestParam("brandName") String brandName) {
        return ResponseEntity.ok(departmentStoreBrandService.getBrandLocations(departmentStoreId, brandName));
    }
}
