package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.src.carts.dto.GetCartsResponse;
import com.example.demo.src.carts.dto.object.Cart;
import com.example.demo.src.users.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CartProvider {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private CartDao cartDao;
    private UserDao userDao;

    @Autowired
    public CartProvider(CartDao cartDao,UserDao userDao) {
        this.cartDao = cartDao;
        this.userDao=userDao;
    }

    // 장바구니 조회
    @Transactional
    public GetCartsResponse getCartList(int userNum)throws BaseException {
        try{
            if(userDao.checkUserNum(userNum)!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
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
            if(userDao.checkUserNum(userNum)!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
           return cartDao.getOrderedCarts(userNum);
        }catch (Exception exception){
            logger.error("App - getOrderedList CartProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유효한 주문 체크
    public void checkOrder(int orderNum)throws BaseException{
        if(cartDao.checkOrder(orderNum)==0){
            throw new BaseException(INACTIVE_ORDER);
        }
    }
}
