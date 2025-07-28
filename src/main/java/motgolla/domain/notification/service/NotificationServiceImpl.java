package motgolla.domain.notification.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.notification.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public void insertToken(Long id, String token) {
        notificationMapper.insertToken(id, token);
    }

    @Override
    public void deleteToken(Long id) {
        notificationMapper.deleteToken(id);
    }
}
