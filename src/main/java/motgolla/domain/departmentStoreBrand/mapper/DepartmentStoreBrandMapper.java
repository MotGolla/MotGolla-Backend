package motgolla.domain.departmentStoreBrand.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentStoreBrandMapper {
    List<String> findBrandLocations(@Param("departmentStoreId") Long departmentStoreId,
                                    @Param("brandName") String brandName);
}
