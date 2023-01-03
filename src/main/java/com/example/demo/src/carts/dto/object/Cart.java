package com.example.demo.src.carts.dto.object;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cart {
    private String thumbnail;
    private int orderCnt;
    private String productName;
    private int price;
    private int productNum;
    private int ordersNum;
}
