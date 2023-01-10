package com.example.demo.src.carts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class PatchOrderCntRequest {

    @Positive
    private int ordersNum;


    @Positive
    private int orderCnt; // 변경할 개수


}
