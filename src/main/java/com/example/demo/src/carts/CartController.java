package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.carts.dto.GetCartsResponse;
import com.example.demo.src.carts.dto.PostOrdersCartRequest;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private CartProvider cartProvider;
    private CartService cartService;

    @Autowired
    public CartController(CartProvider cartProvider, CartService cartService) {
        this.cartProvider = cartProvider;
        this.cartService = cartService;
    }

    // 장바구니에 상품 추가 API(8)
    @PostMapping("")
    public BaseResponse<String> addCart(@RequestParam int userNum, @RequestBody PostOrdersCartRequest postOrdersCart) throws BaseException{
        String result="장바구니 담기 실패";
        if(cartService.addOrderAndCart(userNum,postOrdersCart)){
            result="장바구니 담기 성공";
        }
        return new BaseResponse<>(result);
    }

    // 장바구니 조회 API(7)
    @GetMapping("")
    public BaseResponse<GetCartsResponse> getCarts(@RequestParam int userNum)throws BaseException{
        GetCartsResponse response=cartProvider.getCartList(userNum);
        return new BaseResponse<>(response);
    }

}
