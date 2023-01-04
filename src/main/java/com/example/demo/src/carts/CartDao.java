package com.example.demo.src.carts;

import com.example.demo.src.carts.dto.CreateOrderAndCartSaveDto;
import com.example.demo.src.carts.dto.PatchOrderCntRequest;
import com.example.demo.src.carts.dto.object.Cart;
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
    public int insertOrderAndCart(CreateOrderAndCartSaveDto req){
        int result=0;
        String query="insert into orders (productNum,userNum,orderCnt,price) values(?,?,?,?)";
        Object[] insertParam=new Object[]{req.getProductNum(),req.getUserNum(),req.getOrderCnt(),req.getOrderPrice()};
       if(this.jdbcTemplate.update(query,insertParam)==1){
           String query1="select last_insert_id() as ordersNum";
           int ordersNum=this.jdbcTemplate.queryForObject(query1, new RowMapper<Integer>() {
               @Override
               public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                   return rs.getInt("ordersNum");
               }
           });
           result=this.insertCart(req.getUserNum(),ordersNum);
       }
       return result;
/*        String query1="select last_insert_id() as ordersNum";
        int ordersNum=this.jdbcTemplate.queryForObject(query, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("ordersNum");
            }
        });
        return this.insertCart(req.getUserNum(),ordersNum);*/
    }
    // 장바구니에 추가
    public int insertCart(int userNum,int ordersNum){
        String query="insert into productCartMap (userNum,orderNum) values(?,?)";
        Object[] insertParam=new Object[]{userNum,ordersNum};
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
    public int deleteOrdersAndCart(int ordersNum){
        int result=0;
        String query="delete from productCartMap where orderNum=?";
        if(this.jdbcTemplate.update(query,ordersNum)==1){
            result= this.deleteCart(ordersNum);
        }
        return result;
    }

    // 주문 삭제 API
    public int deleteCart(int ordersNum){
        String query="delete from orders where ordersNum=?";
        return this.jdbcTemplate.update(query,ordersNum);
    }
}
