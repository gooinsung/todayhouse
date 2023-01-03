package com.example.demo.src.carts;

import com.example.demo.src.carts.dto.CreateOrderAndCartSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CartDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 장바구니 추가
    public int insertOrderAndCart(CreateOrderAndCartSaveDto req){
        String query="insert into orders (productNum,userNum,orderCnt,price) values(?,?,?,?)";
        Object[] insertParam=new Object[]{req.getProductNum(),req.getUserNum(),req.getOrderCnt(),req.getOrderPrice()};
        return this.jdbcTemplate.update(query,insertParam);
    }
}
