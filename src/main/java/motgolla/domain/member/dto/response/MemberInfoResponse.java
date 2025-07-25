package motgolla.domain.member.dto.response;

public record MemberInfoResponse(
	Long id,
	String name,
	String birthday,
	String gender,
	String profile,
	String createdAt
) {

}
