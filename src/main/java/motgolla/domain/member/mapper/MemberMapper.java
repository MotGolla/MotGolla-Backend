package motgolla.domain.member.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.dto.request.SocialSignUpRequest;
import motgolla.domain.member.vo.Member;

@Mapper
public interface MemberMapper {
	void insertMember(@Param("request") SignUpRequest signUpRequest);
	void insertSocialMember(
		@Param("request") SocialSignUpRequest signUpRequest,
		@Param("memberType") String memberType,
		@Param("password") String password
	);

	void updateIsDeleted(Long id);
	Optional<Member> findById(Long id);
	Optional<Member> findByOauthId(String oauthId);
	Optional<Member> findByIdAndPassword(@Param("id") String id, @Param("password") String password);
	void updateIsDeletedFalse(@Param("oauthId") String oauthId);
}
