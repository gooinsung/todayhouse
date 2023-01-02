package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProduct {
    private int productNum;
    private String productName;
    private int productPrice;
    private String thumbnail;
    private int productCate;
}
