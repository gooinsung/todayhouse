package com.example.demo.src.products;

import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.dto.PostReviewRequest;
import com.example.demo.src.products.dto.SaveReviewDTO;
import com.example.demo.src.products.model.GetProduct;
import com.example.demo.src.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Product getProductDetail(int productNum){
        String query= "select count(r.reviewNum) as cnt, p.productNum, p.productName, p.productPrice, p.productInfo, p.productCate,p.thumbnail from product p inner join review r on p.productNum=r.productNum where p.productNum=?";
        GetProductDetailResponse response= new GetProductDetailResponse(); 
        return this.jdbcTemplate.queryForObject(query, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product= new Product();
                product.setProductNum(rs.getInt("productNum"));
                product.setProductInfo(rs.getString("productInfo"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getInt("productPrice"));
                product.setProductCate(rs.getInt("productCate"));
                product.setThumbnail(rs.getString("thumbnail"));
                return product;
            }
        },productNum);
    }

    public List<String> getProductImgs(int productNum){
        String query="select storedFilename from productPics where productNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String filename=rs.getString("storedFilename");
                return filename;
            }
        },productNum);
    }

    public int insertReview(SaveReviewDTO dto){
        String query="insert into review (reviewContent,storedFilename,point1,point2,point3,point4,productNum,userNum) values(?,?,?,?,?,?,?,?)";
        Object[] insertReviewParam= new Object[]{dto.getReviewContent(),dto.getFilename(),dto.getPoint1(),dto.getPoint2(),dto.getPoint3(),dto.getPoint4(),dto.getProductNum(),dto.getUserNum()};
        return this.jdbcTemplate.update(query,insertReviewParam);
    }

    public List<GetProduct> getProductList(){
        String query= "select productNum,productName,productPrice,thumbnail,productCate from product";
        return this.jdbcTemplate.query(query, new RowMapper<GetProduct>() {
            @Override
            public GetProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProduct getProduct= new GetProduct();
                getProduct.setProductNum(rs.getInt("productNum"));
                getProduct.setProductName(rs.getString("productName"));
                getProduct.setProductPrice(rs.getInt("productPrice"));
                getProduct.setProductCate(rs.getInt("productCate"));
                getProduct.setThumbnail(rs.getString("thumbnail"));
                return getProduct;
            }
        });

    }
/*      product.setProductNum(rs.getInt("productNum"));
                product.setProductInfo(rs.getString("productInfo"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getInt("productPrice"));
                product.setProductCate(rs.getInt("productCate"));
                product.setThumbnail(rs.getString("thumbnail"));*/

/*    String getUserQuery = "select * from UserInfo where userIdx = ?";
    int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
            (rs, rowNum) -> new GetUserRes(
            rs.getInt("userIdx"),
                        rs.getString("userName"),
                                rs.getString("ID"),
                                rs.getString("Email"),
                                rs.getString("password")),
    getUserParams);*/
}
