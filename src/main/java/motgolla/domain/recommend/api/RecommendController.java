package motgolla.domain.recommend.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import motgolla.domain.recommend.dto.response.RecommendedProduct;
import motgolla.domain.recommend.service.RecommendService;
import motgolla.domain.recommend.service.RecommendUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendUpdater recommendUpdater;
    private final RecommendService recommendService;

    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> refreshAllRecommendations() {
        recommendUpdater.runUpdate();
        return ResponseEntity.ok("추천 결과 갱신 완료");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<RecommendedProduct>> getRecommendedProduct(@PathVariable("productId") Long productId) {
        List<RecommendedProduct> recommendedProducts = recommendService.getRecommendedProducts(productId);
        return ResponseEntity.ok(recommendedProducts);
    }
}
