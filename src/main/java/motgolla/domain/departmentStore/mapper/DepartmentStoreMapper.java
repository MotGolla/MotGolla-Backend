package motgolla.domain.departmentStore.mapper;

import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentStoreMapper {
	DepartmentStoreResponse findNearestStore(double lat, double lon);
}
