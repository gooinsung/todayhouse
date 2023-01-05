package com.example.demo.src.products.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ExamSaveDTO {
    private String productName;
    private int productPrice;
    private String productInfo;
    private int productCate;
    private int productCnt;

    @Builder
    public ExamSaveDTO(ExamRequest req){
        this.productName=req.getProductName();
        this.productPrice=req.getProductPrice();
        this.productInfo=req.getProductInfo();
        this.productCate=req.getProductCate();
        this.productCnt=req.getProductCnt();

    }
}
