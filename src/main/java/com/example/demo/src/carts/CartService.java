package com.example.demo.src.carts;

import com.example.demo.config.BaseException;
import com.example.demo.src.carts.dto.CreateOrderAndCartSaveDto;
import com.example.demo.src.carts.dto.PatchOrderCntRequest;
import com.example.demo.src.carts.dto.PostOrdersCartRequest;
import com.example.demo.src.carts.dto.object.OrderMap;
import com.example.demo.src.products.ProductDao;
import com.example.demo.src.products.dto.object.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CartService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private CartDao cartDao;
    private ProductDao productDao;


    @Autowired
    public CartService(CartDao cartDao,ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    // 주문 및 장바구니에 추가 메서드
    @Transactional
    public boolean addCart(int userNum, PostOrdersCartRequest req)throws BaseException{
        try{
            boolean result= false;
            Product product= productDao.getProductInfo(req.getProductNum());
            CreateOrderAndCartSaveDto dto= new CreateOrderAndCartSaveDto();
            dto.setUserNum(userNum);
            dto.setProductNum(product.getProductNum());
            dto.setOrderCnt(req.getOrderCnt());
            dto.setOrderPrice(product.getProductPrice()*req.getOrderCnt());
            if(cartDao.insertOrder(dto)==1){
                result=true;
            }

            return result;
        }catch (Exception exception){
            logger.error("App - addOrderAndCart CartService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 수량 수정 메서드
    @Transactional
    public boolean updateOrderCnt (PatchOrderCntRequest request) throws BaseException{
        try{
            boolean result=false;
            if(cartDao.updateOrderCnt(request)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - updateOrderCnt CartService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 주문 삭제 메서드
    @Transactional
    public boolean deleteOrders(int ordersNum) throws BaseException{
        try{
            boolean result=false;
            if(cartDao.deleteOrders(ordersNum)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - deleteOrdersAndCart CartService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 주문 메서드
    @Transactional
    public boolean order(int userNum) throws BaseException{
        try{
            boolean result=false;
            List<OrderMap> orderMaps=cartDao.getOrderMap(userNum);
            if(productDao.reduceProductCnt(orderMaps)!=0){
                if(cartDao.changeOrderStatus(userNum)!=0) result= true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - order CartService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
