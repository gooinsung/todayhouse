package com.example.demo.src.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamRequest {
    private String productName;
    private int productPrice;
    private String productInfo;
    private int productCate;
    private int productCnt;
}
