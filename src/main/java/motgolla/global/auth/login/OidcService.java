package motgolla.global.auth.login;

import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.*;

import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;

@Service
public class OidcService {

	private static final String KAKAO_JWKS_URI = "https://kauth.kakao.com/.well-known/jwks.json";
	private static final String KAKAO_ISSUER = "https://kauth.kakao.com";

	@Value("${kakao.rest-api-key}")
	private String CLIENT_ID; // OIDC client_id

	public Map<String, Object> verify(String idToken) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(idToken);

			// JWK를 이용한 서명 검증
			JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(KAKAO_JWKS_URI));
			ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
			JWSKeySelector<SecurityContext> keySelector =
				new JWSVerificationKeySelector<>(signedJWT.getHeader().getAlgorithm(), keySource);

			jwtProcessor.setJWSKeySelector(keySelector);

			// 검증 후 claims 추출
			JWTClaimsSet claimsSet = jwtProcessor.process(signedJWT, null);

			// issuer, audience 검증 (카카오 기준)
			if (!claimsSet.getIssuer().equals(KAKAO_ISSUER) || !claimsSet.getAudience().contains(CLIENT_ID)) {
				throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
			}

			return claimsSet.getClaims();

		} catch (Exception e) {
			throw new RuntimeException("idToken 검증 실패", e);
		}
	}
}
