package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.carts.dto.PostOrdersCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 장바구니에 상품 추가 API
    @PostMapping("")
    public BaseResponse<String> addCart(@RequestParam int userNum, @RequestBody PostOrdersCart postOrdersCart) throws BaseException{
        String result="장바구니 담기 실패";
        if(cartService.addOrderAndCart(userNum,postOrdersCart)){
            result="장바구니 담기 성공";
        }
        return new BaseResponse<>(result);
    }
}
