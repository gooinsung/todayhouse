package com.example.demo.src.products.dto.object;

import lombok.*;

@Data
@NoArgsConstructor
public class Product{
    private int productNum;
    private String productName;
    private int productPrice;
    private String productInfo;
    private int productCate;
    private String thumbnail;
    private int reviewCnt;
}
