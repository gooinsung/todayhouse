package com.example.demo.src.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetHomeProduct {
    private int productNum;
    private String productThumbnail;
    private String  productName;
}
