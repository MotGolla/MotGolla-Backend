package motgolla.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.notification.mapper.NotificationMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {
    private final NotificationMapper notificationMapper;
    private final FirebaseMessaging firebaseMessaging;

    //매일 22시에 실행
    @Scheduled(cron = "0 0 22 * * *") // 초 분 시 일 월 요일
    //테스트용 :
    //@Scheduled(cron = "0 10 * * * *") // 초 분 시 일 월 요일
    public void sendDailyReminder() {
        log.info("💬 [FCM Scheduler] 22시 알림 전송 시작");

        // 1. 오늘 상품 등록한 유저 ID 조회
        List<Long> userIds = notificationMapper.findUserIdsWhoRegisteredToday();
        log.info("📦 오늘 상품 등록한 사용자 수: {}", userIds.size());

        for (Long userId : userIds) {
            try {
                // 2. 유저의 FCM 토큰 조회
                String token = notificationMapper.findTokenByUserId(userId);
                if (token == null || token.isBlank()) {
                    log.warn("⚠️ userId {} 는 토큰이 없습니다", userId);
                    continue;
                }

                // 3. FCM 메시지 생성
                Notification notification = Notification.builder()
                        .setTitle("오늘 등록한 상품 확인해보세요!")
                        .setBody("기록한 상품을 다시 확인해보세요 😊")
                        .build();

                Message message = Message.builder()
                        .setToken(token)
                        .setNotification(notification)
                        .putData("click_action", "FCM_CLICK")
                        .build();

                // 4. 전송
                String response = firebaseMessaging.send(message);
                log.info("✅ userId {} 푸시 전송 성공: {}", userId, response);

            } catch (FirebaseMessagingException e) {
                log.error("❌ FCM 전송 실패 - userId: " + userId, e);
            }
        }

        log.info("✅ [FCM Scheduler] 22시 알림 전송 완료");
    }

}
