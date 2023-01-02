package com.example.demo.src.products;

import com.example.demo.src.products.dto.*;
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

    // 게시글 상세정보 가져오기
    public Product getProductDetail(int productNum){
        String query= "select count(r.reviewNum) as cnt, p.productNum, p.productName, p.productPrice, p.productInfo, p.productCate, p.thumbnail from product p left join review r on p.productNum= r.productNum where p.productNum=?";
        GetProductDetailResponse response= new GetProductDetailResponse(); 
        return this.jdbcTemplate.queryForObject(query, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product= new Product();
                product.setReviewCnt(rs.getInt("cnt"));
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

    // 게시글 상세정보를 위한 이미지 가져오기
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

    // 리뷰 작성
    public int insertReview(SaveReviewDTO dto){
        String query="insert into review (reviewContent,storedFilename,point1,point2,point3,point4,productNum,userNum) values(?,?,?,?,?,?,?,?)";
        Object[] insertReviewParam= new Object[]{dto.getReviewContent(),dto.getFilename(),dto.getPoint1(),dto.getPoint2(),dto.getPoint3(),dto.getPoint4(),dto.getProductNum(),dto.getUserNum()};
        return this.jdbcTemplate.update(query,insertReviewParam);
    }

    // 게시글 리스트 가져오기
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









    // 테스트 데이터 작성 메서드
    public int postProduct(ExamSaveDTO req,List<String> filenames){
        int result=0;
        String query="insert into product (productName,productPrice,productInfo,productCate,productCnt,thumbnail) values(?,?,?,?,?,?)";
        Object[] insertParam= new Object[]{req.getProductName(),req.getProductPrice(),req.getProductInfo(),req.getProductCate(),req.getProductCnt(),req.getThumbnail()};
        if(this.jdbcTemplate.update(query,insertParam)==1){
            String query1="select productNum from product where productName=?";
            int productNum=this.jdbcTemplate.queryForObject(query1, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt("productNum");
                }
            },req.getProductName());
            this.postProductPics(filenames,productNum);
            result=1;
        }
        return result;

    }
    public void postProductPics(List<String> filenames,int productNum){
        String query="insert into productPics (storedFilename,productNum) values(?,?)";
        for(String filename:filenames){
            Object[] insertParam= new Object[]{filename,productNum};
            this.jdbcTemplate.update(query,insertParam);
        }
    }

}
