package motgolla.domain.recommend.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.recommend.dto.request.ProductRequest;
import motgolla.domain.recommend.infra.LambdaRecommendClient;
import motgolla.domain.recommend.mapper.RecommendMapper;
import motgolla.domain.product.vo.Product;
import motgolla.domain.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendUpdater {

    private final ProductMapper productMapper;
    private final RecommendMapper recommendMapper;
    private final LambdaRecommendClient lambdaClient;

    public void runUpdate() {
        List<Product> allProducts = productMapper.findAllForRecommend();

        for (Product product : allProducts) {
            ProductRequest dto = ProductRequest.from(product);
            List<String> recommendedIds = lambdaClient.getRecommendations(dto);
            recommendMapper.saveRecommendations(product.getId(), recommendedIds);
        }
    }
}

