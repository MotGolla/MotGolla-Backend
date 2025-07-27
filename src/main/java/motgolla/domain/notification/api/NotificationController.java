package motgolla.domain.notification.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.vo.Member;
import motgolla.domain.notification.dto.request.FcmTokenRequest;
import motgolla.domain.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "알림 API", description = "알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    //fcm 토큰 저장
    @PostMapping("/token")
    @Operation(summary = "FCM토큰 저장", description = "사용자별 FCM토큰 저장")
    public ResponseEntity<Map<String, String>> registerToken(
            @AuthenticationPrincipal Member member,
            @RequestBody FcmTokenRequest request) {
        notificationService.insertToken(member.getId(), request.getToken());
        Map<String, String> response = new HashMap<>();
        response.put("message", "성공");
        return ResponseEntity.ok(response);
    }
}