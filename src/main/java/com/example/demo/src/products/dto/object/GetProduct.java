package com.example.demo.src.products.dto.object;

import lombok.*;

@Data
@NoArgsConstructor
public class GetProduct {
    private int productNum;
    private String productName;
    private int productPrice;
    private String thumbnail;
    private int productCate;
}
