package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.carts.dto.GetCartsResponse;
import com.example.demo.src.carts.dto.PatchOrderCntRequest;
import com.example.demo.src.carts.dto.PostOrdersCartRequest;
import com.example.demo.src.carts.dto.object.Cart;
import com.example.demo.src.users.UserProvider;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private CartProvider cartProvider;
    private CartService cartService;
    private UserProvider userProvider;

    @Autowired
    public CartController(CartProvider cartProvider, CartService cartService,UserProvider userProvider) {
        this.cartProvider = cartProvider;
        this.cartService = cartService;
        this.userProvider = userProvider;
    }

    // 장바구니 조회 API(7)
    @GetMapping("")
    public BaseResponse<GetCartsResponse> getCarts(@RequestParam int userNum)throws BaseException{
        logger.info("CartController 내 7번 API 실행");
        userProvider.checkUserNum(userNum);
        GetCartsResponse response=cartProvider.getCartList(userNum);
        return new BaseResponse<>(response);
    }

    // 장바구니에 상품 추가 API(8)
    @PostMapping("")
    public BaseResponse<String> addCart(@RequestParam int userNum, @RequestBody @Validated PostOrdersCartRequest postOrdersCart) throws BaseException{
        logger.info("CartController 내 8번 API 실행");
        userProvider.checkUserNum(userNum);
        String result="장바구니 담기 실패";
        if(cartService.addCart(userNum,postOrdersCart)){
            result="장바구니 담기 성공";
        }
        return new BaseResponse<>(result);
    }

    // 주문 상품 수량 수정 API(9)
    @PatchMapping("")
    public BaseResponse<String> changeOrderCnt(@RequestParam int userNum,@RequestBody @Validated PatchOrderCntRequest patchOrderCntRequest) throws BaseException{
        logger.info("CartController 내 9번 API 실행");
        userProvider.checkUserNum(userNum);
        cartProvider.checkOrder(patchOrderCntRequest.getOrdersNum());
        String result="상품 개수 수정 실패";
        if(cartService.updateOrderCnt(patchOrderCntRequest)){
            result="상품 수정 성공!";
        }
        return new BaseResponse<>(result);
    }

    // 상품 주문 (10)API
    @PostMapping("/orders/{userNum}")
    public BaseResponse<String> postOrders(@PathVariable int userNum) throws BaseException{
        logger.info("CartController 내 10번 API 실행");
        userProvider.checkUserNum(userNum);
        String result="상품 주문 실패";
        if(cartService.order(userNum)){
            result="상품 주문 성공!";
        }
        return new BaseResponse<>(result);
    }

    // 주문 삭제 API(22)
    @DeleteMapping("")
    public BaseResponse<String> deleteOrder(@RequestParam int ordersNum) throws BaseException{
        logger.info("CartController 내 22번 API 실행");
        cartProvider.checkOrder(ordersNum);
        String result="주문 삭제 실패";
        if(cartService.deleteOrders(ordersNum)){
            result="주문 삭제 성공";
        }
        return new BaseResponse<>(result);
    }

    // 주문했던 상품 조회 API(37)
    @GetMapping("/ordered/{userNum}")
    public BaseResponse<List<Cart>> getOrderedList(@PathVariable int userNum) throws BaseException{
        logger.info("CartController 내 37번 API 실행");
        userProvider.checkUserNum(userNum);
        List<Cart> orderedCartList= new ArrayList<>();
        orderedCartList=cartProvider.getOrderedList(userNum);
        return new BaseResponse<>(orderedCartList);
    }



}
