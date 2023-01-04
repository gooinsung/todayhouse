package com.example.demo.src.products.dto;

import com.example.demo.src.products.dto.object.GetProduct;
import lombok.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
