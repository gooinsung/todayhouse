package com.example.demo.src.carts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderAndCartSaveDto {
    private int userNum;
    private int productNum;
    private int orderCnt;
    private int orderPrice;
}
