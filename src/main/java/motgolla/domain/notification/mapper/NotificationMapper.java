package motgolla.domain.notification.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    void insertToken(@Param("memberId") Long memberId, @Param("token") String token);
    List<Long> findUserIdsWhoRegisteredToday();
    String findTokenByUserId(@Param("memberId") Long memberId);
    void deleteToken(@Param("memberId") Long id);
}