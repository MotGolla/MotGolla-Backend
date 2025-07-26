package motgolla.domain.recommend.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.recommend.dto.request.ProductRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LambdaRecommendClient {

    @Value("${recommend.lambda.url}")
    private String baseUrl;

    @Value("${recommend.lambda.path}")
    private String path;

    @Value("${recommend.lambda.api-key}")
    private String apiKey;

    private final WebClient.Builder webClientBuilder;

    public List<String> getRecommendations(ProductRequest productDto) {
        return webClientBuilder.build()
                .post()
                .uri(baseUrl + path)
                .header("x-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productDto)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class).map(body -> {
                            log.error("Lambda 호출 실패: {}", body);
                            return new RuntimeException("Lambda 호출 실패: " + body);
                        })
                )
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }
}

