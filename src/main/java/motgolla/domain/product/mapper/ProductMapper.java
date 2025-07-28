package motgolla.domain.product.mapper;

import motgolla.domain.product.vo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAllForRecommend();
    boolean existsById(@Param("id") Long id);
}
