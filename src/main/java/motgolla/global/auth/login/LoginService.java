package motgolla.global.auth.login;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.mapper.MemberMapper;
import motgolla.domain.member.vo.Member;
import motgolla.global.error.ErrorCode;
import motgolla.global.util.HashUtil;

/**
 *    파라미터로 username으로 DB에서 일치하는 Member를 찾고,
 *    해당 회원의 username과 Role을 담아 UserDetails의 User 객체를 생성한다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberMapper memberMapper;

    /**
     * 파라미터 socialId를 해싱 처리한 후 db에서 일치하는 사용자를 조회한다.
     * 비밀번호는 인증 절차에 필요하지 않으므로 난수로 만든 다음 UserDetail 객체로 넘긴다.
     */

    @Override
    public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
        log.info("loadUserByUsername 진입");
        log.info("oauthId: {}", oauthId);
        String hashedOauthId = HashUtil.hash(oauthId);
        Optional<Member> member = memberMapper.findByOauthId(hashedOauthId);

        if (member.isPresent()) {
            Member userDetails = member.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime updatedAt = LocalDateTime.parse(userDetails.getUpdatedAt(), formatter);

            if (userDetails.getIsDeleted() == 1 && updatedAt.isAfter(LocalDateTime.now().minusMonths(6))) {
                throw new UsernameNotFoundException(ErrorCode.RECENT_RESIGNED_MEMBER.getCode());
            }

            if(userDetails.getIsDeleted() == 1 && updatedAt.isBefore(LocalDateTime.now())) {
                throw new UsernameNotFoundException(ErrorCode.LOGIN_FAILED.getCode());
            }

            return member.get();
        }
        else{
            throw new UsernameNotFoundException(ErrorCode.LOGIN_FAILED.getCode());
        }
    }

}
