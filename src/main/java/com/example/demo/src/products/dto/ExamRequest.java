package com.example.demo.src.products.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ExamRequest {
    private String productName;
    private int productPrice;
    private String productInfo;
    private int productCate;
    private int productCnt;
}
