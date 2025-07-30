package motgolla.domain.departmentStore.mapper;

import motgolla.domain.departmentStore.dto.response.DepartmentStoreIdResponse;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentStoreMapper {
	DepartmentStoreResponse findNearestStore(double lat, double lon);

	List<DepartmentStoreIdResponse> findAllStores();

	boolean existsStore(@Param("departmentStoreId") Long departmentStoreId);
}
