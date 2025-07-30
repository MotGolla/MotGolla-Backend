package motgolla.domain.product.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.product.service.ProductService;
import motgolla.domain.recommend.dto.response.RecommendedProduct;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@Operation(
		summary = "추천 상품 조회",
		description = "특정 상품 ID에 기반하여 추천 상품 목록을 조회합니다."
	)
	@GetMapping("/{productId}/recommend")
	public ResponseEntity<List<RecommendedProduct>> getRecommendedProducts(
		@PathVariable("productId") Long productId,
		@RequestParam("departmentStoreId") Long departmentStoreId) {
		List<RecommendedProduct> result = productService.findRecommendationsByProductId(productId, departmentStoreId);
		return ResponseEntity.ok(result);
	}

}
