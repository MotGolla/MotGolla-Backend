package motgolla.domain.member.dto.request;

public record LoginRequest(
	String idToken,
	String oauthId
) {
}
