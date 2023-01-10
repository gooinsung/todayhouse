package com.example.demo.src.carts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class PostOrdersCartRequest {

    @Positive
    private int productNum;

    @Positive
    private int orderCnt;
}
