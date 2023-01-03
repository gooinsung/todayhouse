package com.example.demo.src.carts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProvider {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private CartDao cartDao;

    @Autowired
    public CartProvider(CartDao cartDao) {
        this.cartDao = cartDao;
    }
}
