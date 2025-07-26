package motgolla.domain.recommend.api;

import lombok.RequiredArgsConstructor;
import motgolla.domain.recommend.service.RecommendUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendUpdater recommendUpdater;

    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> refreshAllRecommendations() {
        recommendUpdater.runUpdate();
        return ResponseEntity.ok("추천 결과 갱신 완료");
    }
}
