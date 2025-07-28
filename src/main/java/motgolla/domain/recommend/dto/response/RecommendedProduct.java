package motgolla.domain.recommend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedProduct{
	private Long id;
	private String name;
	private String brand;
	private int floor;
	private int price;
}
