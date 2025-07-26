package motgolla.domain.product.mapper;

import motgolla.domain.product.vo.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAllForRecommend();
}
