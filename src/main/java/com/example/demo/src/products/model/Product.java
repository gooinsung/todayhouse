package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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
