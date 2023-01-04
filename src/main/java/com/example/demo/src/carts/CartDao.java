package com.example.demo.src.carts;

import com.example.demo.src.carts.dto.CreateOrderAndCartSaveDto;
import com.example.demo.src.carts.dto.PatchOrderCntRequest;
import com.example.demo.src.carts.dto.object.Cart;
import com.example.demo.src.carts.dto.object.OrderMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    // 주문 추가
    @Transactional
    public int insertOrder(CreateOrderAndCartSaveDto req){
        int result=0;
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


    // 상품 주문 수량 수정
    public int updateOrderCnt(PatchOrderCntRequest req){
        String query="update orders set orderCnt=? where ordersNum=?";
        Object[] updateParam=new Object[]{req.getOrderCnt(),req.getOrdersNum()};
        return this.jdbcTemplate.update(query,updateParam);
    }

    // 장바구니 삭제
    @Transactional
    public int deleteOrders(int ordersNum){
        String query="update orders set status='inactive' where ordersNum=?";
        return this.jdbcTemplate.update(query,ordersNum);
    }

    // 장바구니 주문하기
    public List<OrderMap> getOrderMap(int userNum){
        String query="select productNum,orderCnt from orders where status='active' userNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<OrderMap>() {
            @Override
            public OrderMap mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderMap res= new OrderMap();
                res.setProductNum(rs.getInt("productNum"));
                res.setOrderCnt(rs.getInt("orderCnt"));
                return res;
            }
        },userNum);
    }

    // 장바구니 주문
    public int changeOrderStatus(int userNum){
        String query="update orders set status='ordered' where userNum=?";
        return this.jdbcTemplate.update(query,userNum);
    }



}
