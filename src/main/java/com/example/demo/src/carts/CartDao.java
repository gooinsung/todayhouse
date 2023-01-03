package com.example.demo.src.carts;

import com.example.demo.src.carts.dto.CreateOrderAndCartSaveDto;
import com.example.demo.src.carts.dto.object.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    // 장바구니 조회
    public List<Cart> getCarts(int userNum){
        String query="select p.productNum, p.thumbnail, p.productName, o.price, o.ordersNum, o.orderCnt from orders o inner join product p on o.productNum=p.productNum where o.userNum=? ";
        return this.jdbcTemplate.query(query, new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart cart= new Cart();
                cart.setProductNum(rs.getInt("productNum"));
                cart.setThumbnail(rs.getString("thumbnail"));
                cart.setProductName(rs.getString("productName"));
                cart.setPrice(rs.getInt("price"));
                cart.setOrdersNum(rs.getInt("ordersNum"));
                cart.setOrderCnt(rs.getInt("orderCnt"));

                return cart;
            }
        },userNum);
    }
}
