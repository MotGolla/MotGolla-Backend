package motgolla.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest{
	private Long id;
	private String oauthId;
	private String idToken;
	private String name;
	private String gender;
	private String birthday;
}
