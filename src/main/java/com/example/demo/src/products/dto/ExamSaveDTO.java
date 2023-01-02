package com.example.demo.src.products.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamSaveDTO {
    private String productName;
    private int productPrice;
    private String productInfo;
    private int productCate;
    private int productCnt;
    private String thumbnail;

    @Builder
    public ExamSaveDTO(ExamRequest req,String thumbnail){
        this.productName=req.getProductName();
        this.productPrice=req.getProductPrice();
        this.productInfo=req.getProductInfo();
        this.productCate=req.getProductCate();
        this.productCnt=req.getProductCnt();
        this.thumbnail=thumbnail;
    }
}
