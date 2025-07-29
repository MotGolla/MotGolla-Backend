package motgolla.domain.departmentStoreBrand.mapper;

import motgolla.domain.departmentStoreBrand.dto.response.BrandLocationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentStoreBrandMapper {
    List<BrandLocationResponse> findBrandLocations(@Param("departmentStoreId") Long departmentStoreId,
                                                   @Param("brandName") String brandName);
}
