package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.src.carts.dto.GetCartsResponse;
import com.example.demo.src.carts.dto.object.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CartProvider {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private CartDao cartDao;

    @Autowired
    public CartProvider(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    // 장바구니 조회
    @Transactional
    public GetCartsResponse getCartList(int userNum)throws BaseException {
        try{
            GetCartsResponse response=new GetCartsResponse();
            List<Cart> cartList= cartDao.getCarts(userNum);
            int totalPrice=0;
            for (Cart cart:cartList){
                totalPrice+=cart.getPrice();
            }
            response.setCartList(cartList);
            response.setTotalPrice(totalPrice);
            response.setUserNum(userNum);
            return response;
        }catch (Exception exception){
            logger.error("App - getCartList CartProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주문했던 상품 조회
    @Transactional
    public List<Cart> getOrderedList(int userNum) throws BaseException{
        try{
           return cartDao.getOrderedCarts(userNum);
        }catch (Exception exception){
            logger.error("App - getOrderedList CartProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
