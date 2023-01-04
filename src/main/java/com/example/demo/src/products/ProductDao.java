package com.example.demo.src.products;

import com.example.demo.src.carts.dto.object.OrderMap;
import com.example.demo.src.products.dto.*;
import com.example.demo.src.products.dto.object.Product;
import com.example.demo.src.products.dto.object.SaveReviewDTO;
import com.example.demo.src.products.dto.object.UpdateReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        String query= "select count(r.reviewNum) as cnt, p.productNum, p.productName, p.productPrice, p.productInfo, p.productCate, p.thumbnail, p.productCom, r.pointAvg from product p left join review r on p.productNum= r.productNum where p.status='active' and p.productNum=?";
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
                product.setProductCom(rs.getString("productCom"));
                product.setReviewAvg(rs.getFloat("pointAvg"));
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

    // 리뷰 조회
    public List<GetReviewResponse> getReviews(int productNum){
        String query="select r.reviewNum, r.point1, r.point2, r.point3, r.point4, r.pointAvg, r.createdAt, r.storedFilename, r.reviewContent, u.userNickName as userNickName from review r inner join user u on r.userNum=u.userNum where r.status='active' and r.productNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<GetReviewResponse>() {
            @Override
            public GetReviewResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetReviewResponse response= new GetReviewResponse();
                response.setReviewNum(rs.getInt("reviewNum"));
                response.setPoint1(rs.getInt("point1"));
                response.setPoint2(rs.getInt("point2"));
                response.setPoint3(rs.getInt("point3"));
                response.setPoint4(rs.getInt("point4"));
                response.setPointAvg(rs.getFloat("pointAvg"));
                response.setCreatedAt(rs.getTimestamp("createdAt"));
                response.setReviewImg(rs.getString("storedFilename"));
                response.setReviewContent(rs.getString("reviewContent"));
                response.setUserNickName(rs.getString("userNickName"));
                return response;
            }
        },productNum);
    }

    // 특정 리뷰 조회
    public GetReviewResponse getReview(int reviewNum){
        String query="select r.reviewNum, r.point1, r.point2, r.point3, r.point4, r.pointAvg, r.createdAt, r.storedFilename, r.reviewContent, u.userNickName as userNickName from review r inner join user u on r.userNum=u.userNum where r.status='active' and r.reviewNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<GetReviewResponse>() {
            @Override
            public GetReviewResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetReviewResponse response= new GetReviewResponse();
                response.setReviewNum(rs.getInt("reviewNum"));
                response.setPoint1(rs.getInt("point1"));
                response.setPoint2(rs.getInt("point2"));
                response.setPoint3(rs.getInt("point3"));
                response.setPoint4(rs.getInt("point4"));
                response.setPointAvg(rs.getFloat("pointAvg"));
                response.setCreatedAt(rs.getTimestamp("createdAt"));
                response.setReviewImg(rs.getString("storedFilename"));
                response.setReviewContent(rs.getString("reviewContent"));
                response.setUserNickName(rs.getString("userNickName"));
                return response;
            }
        },reviewNum);
    }

    // 리뷰 작성
    public int insertReview(SaveReviewDTO dto){
        String query="insert into review (reviewContent,storedFilename,point1,point2,point3,point4,productNum,userNum,pointAvg) values(?,?,?,?,?,?,?,?,?)";
        Object[] insertReviewParam= new Object[]{dto.getReviewContent(),dto.getFilename(),dto.getPoint1(),dto.getPoint2(),dto.getPoint3(),dto.getPoint4(),dto.getProductNum(),dto.getUserNum(),dto.getPointAvg()};
        return this.jdbcTemplate.update(query,insertReviewParam);
    }

    // 리뷰 수정
    public int updateReview(UpdateReviewDTO dto){
        String query="update review set reviewContent=?,storedFilename=?, point1=?, point2=?, point3=?, point4=?, pointAvg=? where reviewNum=?";
        Object [] updateParam=new Object[]{dto.getReviewContent(),dto.getFilename(),dto.getPoint1(),dto.getPoint2(),dto.getPoint3(),dto.getPoint4(),dto.getPointAvg(),dto.getReviewNum()};
        return this.jdbcTemplate.update(query,updateParam);
    }

    // 상품 리스트 가져오기
    public List<GetProductResponse> getProductList(){
        String query= "select p.productNum, p.productName, p.productPrice, p.thumbnail, p.productCate,p.productCom, (select count(r.reviewNum) from review r where r.productNum=p.productNum) as reviewCnt, (select avg(r.pointAvg) from review r where r.productNum=p.productNum) as reviewAvg from product p where p.status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<GetProductResponse>() {
            @Override
            public GetProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProductResponse response= new GetProductResponse();
                response.setProductNum(rs.getInt("productNum"));
                response.setProductName(rs.getString("productName"));
                response.setProductPrice(rs.getInt("productPrice"));
                response.setProductCate(rs.getInt("productCate"));
                response.setThumbnail(rs.getString("thumbnail"));
                response.setReviewCnt(rs.getInt("reviewCnt"));
                response.setProductCom(rs.getString("productCom"));
                response.setReviewAvg(rs.getFloat("reviewAvg"));
                return response;
            }
        });

    }

    // 상품 정보 가져오는 메서드
    public Product getProductInfo(int productNum){
        String query="select productNum,productPrice from product where status='active' and productNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product= new Product();
                product.setProductNum(rs.getInt("productNum"));
                product.setProductPrice(rs.getInt("productPrice"));
                return product;
            }
        },productNum);
    }

    // 리뷰 삭제 메서드
    public int deleteReview(int reviewNum){
        String query="update review set status='inactive' where reviewNum=?";
        return this.jdbcTemplate.update(query,reviewNum);
    }



    public int reduceProductCnt(List<OrderMap> maps){
        String query="update product set productCnt=productCnt-? where productNum=?";
        int cnt=0;
        for(OrderMap map:maps){
            Object[] updateParam= new Object[]{map.getOrderCnt(),map.getProductNum()};
            if(this.jdbcTemplate.update(query,updateParam)==1) cnt++;
        }
        return cnt;
    }








    // 테스트 데이터 작성 메서드
    public int postProduct(ExamSaveDTO req,List<String> filenames){
        int result=0;
        String query="insert into product (productName,productPrice,productInfo,productCate,productCnt,thumbnail) values(?,?,?,?,?,?)";
        Object[] insertParam= new Object[]{req.getProductName(),req.getProductPrice(),req.getProductInfo(),req.getProductCate(),req.getProductCnt(),req.getThumbnail()};
        if(this.jdbcTemplate.update(query,insertParam)==1){
            String query1="select last_insert_id() as productNum";
            int productNum=this.jdbcTemplate.queryForObject(query1, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt("productNum");
                }
            });
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
