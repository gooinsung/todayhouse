package com.example.demo.src.carts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class PostOrdersCartRequest {

    @NotBlank
    @Positive
    private int productNum;

    @NotBlank
    @Positive
    private int orderCnt;
}
