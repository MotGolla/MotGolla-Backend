package motgolla.domain.member.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import motgolla.domain.member.dto.request.SignUpRequest;
import motgolla.domain.member.vo.Member;

@Mapper
public interface MemberMapper {
	void insertMember(@Param("request") SignUpRequest signUpRequest);
	void updateIsDeleted(Long id);
	Optional<Member> findById(Long id);
	Optional<Member> findByOauthId(String oauthId);
}
