package motgolla.domain.recommend.dto.request;

import lombok.Data;
import motgolla.domain.product.vo.Product;

@Data
public class ProductRequest {
    private Long productId;
    private String product_name;
    private Long brand;
    private Integer price;
    private String color;
    private String description;
    private String category;
    private String style;
    private String fit;
    private String season;
    private String image_url;

    public static ProductRequest from(Product product) {
        ProductRequest dto = new ProductRequest();
        dto.setProductId(product.getId());
        dto.setProduct_name(product.getName());
        dto.setBrand(product.getBrandId());
        dto.setPrice(product.getPrice());
        dto.setColor(product.getColor());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setStyle(product.getStyle());
        dto.setFit(product.getFit());
        dto.setSeason(product.getSeason());
        dto.setImage_url(product.getImageUrl());
        return dto;
    }
}