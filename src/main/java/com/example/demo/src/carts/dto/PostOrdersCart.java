package com.example.demo.src.carts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOrdersCart {
    private int productNum;
    private int orderCnt;
}
