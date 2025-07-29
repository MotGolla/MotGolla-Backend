package motgolla.domain.product.vo;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Long brandId;
    private Integer price;
    private String productNumber;
    private String barcodeNumber;
    private String description;
    private String category;
    private String style;
    private String fit;
    private String season;
    private String color;
    private String imageUrl;
}
