package motgolla.domain.notification.service;

public interface NotificationService {

	void insertToken(Long id, String token);
	void deleteToken(Long id);
}
