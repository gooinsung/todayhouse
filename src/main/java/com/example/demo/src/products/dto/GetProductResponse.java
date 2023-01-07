package com.example.demo.src.products.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class GetProductResponse {
    private int productNum;
    private String productName;
    private int productPrice;
    private String thumbnail;
    private int productCate;
    private int reviewCnt;
    private String productCom;
    private float reviewAvg;
}
