package com.example.demo.src.carts.dto;

import com.example.demo.src.carts.dto.object.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetCartsResponse {
    private List<Cart> cartList;
    private int userNum;
    private int totalPrice;
}
