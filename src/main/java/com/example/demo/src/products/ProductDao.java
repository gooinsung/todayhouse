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

    // 상품번호 유효성 체크
    public int checkProductNum(int productNum){
        String query="select exists(select * from product where status='active' and productNum=?)";
        return this.jdbcTemplate.queryForObject(query,int.class,productNum);
    }

    // 리뷰 유효성 체크
    public int checkReview(int reviewNum){
        String query="select exists(select * from review where status='active' and reviewNum=?)";
        return this.jdbcTemplate.queryForObject(query,int.class,reviewNum);
    }

    // 게시글 상세정보 가져오기
    public Product getProductDetail(int productNum){
        String query= "select count(r.reviewNum) as cnt, p.productNum, p.productName, p.productPrice, p.productInfo, p.productCate, p.productCom, r.pointAvg from product p left join review r on p.productNum= r.productNum where p.status='active' and p.productNum=?";
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
                product.setProductCom(rs.getString("productCom"));
                product.setReviewAvg(rs.getFloat("pointAvg"));
                return product;
            }
        },productNum);
    }

    // 게시글 썸네일 이미지 가져오기
    public List<String> getThumbnails(int productNum){
        String query="select thumbnail from productThumbnails where productNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("thumbnail");
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
        String query="select r.reviewNum, r.point1, r.point2, r.point3, r.point4, r.pointAvg, r.createdAt, r.storedFilename, r.reviewContent, u.userNickName , p.productName from review r inner join user u on r.userNum=u.userNum inner join product p on r.productNum=p.productNum where r.status='active' and r.productNum=?";
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
                response.setProductName(rs.getString("productName"));
                return response;
            }
        },productNum);
    }

    // 내가 쓴 리뷰 조회
    public List<GetReviewResponse> getMyReviews(int userNum){
        String query="select r.reviewNum, r.point1, r.point2, r.point3, r.point4, r.pointAvg, r.createdAt, r.storedFilename, r.reviewContent, u.userNickName , p.productName from review r inner join user u on r.userNum=u.userNum inner join product p on r.productNum=p.productNum where r.status='active' and r.userNum=?";
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
                response.setProductName(rs.getString("productName"));
                return response;
            }
        },userNum);
    }

    // 특정 리뷰 조회
    public GetReviewResponse getReview(int reviewNum){
        String query="select r.reviewNum, r.point1, r.point2, r.point3, r.point4, r.pointAvg, r.createdAt, r.storedFilename, r.reviewContent, u.userNickName, (select p.productName from product p where p.productNum=r.productNum) as productName from review r inner join user u on r.userNum=u.userNum where r.status='active' and r.reviewNum=?";
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
                response.setProductName(rs.getString("productName"));
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

    // 리뷰작성시, 내가 구매했던 물품인지 확인하는 메서드
    public int checkOrdered(int userNum,int productNum){
        String query="select exists(select productNum from orders where status='ordered' and userNum=? and productNum=?)";
        Object[] selectParam= new Object[]{userNum,productNum};
        return this.jdbcTemplate.queryForObject(query,int.class ,selectParam);
    }

    // 리뷰 수정
    public int updateReview(UpdateReviewDTO dto){
        String query="update review set reviewContent=?,storedFilename=?, point1=?, point2=?, point3=?, point4=?, pointAvg=? where reviewNum=?";
        Object [] updateParam=new Object[]{dto.getReviewContent(),dto.getFilename(),dto.getPoint1(),dto.getPoint2(),dto.getPoint3(),dto.getPoint4(),dto.getPointAvg(),dto.getReviewNum()};
        return this.jdbcTemplate.update(query,updateParam);
    }

    // 상품 리스트 가져오기
    public List<GetProductResponse> getProductList(){
        String query= "select p.productNum, p.productName, p.productPrice, p.productCate,p.productCom, (select count(r.reviewNum) from review r where r.productNum=p.productNum) as reviewCnt, (select avg(r.pointAvg) from review r where r.productNum=p.productNum) as reviewAvg,(select t.thumbnail from productThumbnails t where t.productNum=p.productNum limit 1) as thumbnail from product p where p.status='active'";
        GetProductResponse response= new GetProductResponse();
        return this.jdbcTemplate.query(query, new RowMapper<GetProductResponse>() {
            @Override
            public GetProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProductResponse response= new GetProductResponse();
                response.setProductNum(rs.getInt("productNum"));
                response.setProductName(rs.getString("productName"));
                response.setProductPrice(rs.getInt("productPrice"));
                response.setProductCate(rs.getInt("productCate"));
                response.setReviewCnt(rs.getInt("reviewCnt"));
                response.setProductCom(rs.getString("productCom"));
                response.setReviewAvg(rs.getFloat("reviewAvg"));
                response.setThumbnail(rs.getString("thumbnail"));

                return response;
            }
        });
    }

    // 상품 검색 리스트 가져오기
    public List<GetProductResponse> getProductListBySearch(String search){
        String query= "select p.productNum, p.productName, p.productPrice, p.productCate,p.productCom, (select count(r.reviewNum) from review r where r.productNum=p.productNum) as reviewCnt, (select avg(r.pointAvg) from review r where r.productNum=p.productNum) as reviewAvg,(select t.thumbnail from productThumbnails t where t.productNum=p.productNum limit 1) as thumbnail from product p where p.status='active' and productName like ?";
        GetProductResponse response= new GetProductResponse();
        return this.jdbcTemplate.query(query, new RowMapper<GetProductResponse>() {
            @Override
            public GetProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProductResponse response= new GetProductResponse();
                response.setProductNum(rs.getInt("productNum"));
                response.setProductName(rs.getString("productName"));
                response.setProductPrice(rs.getInt("productPrice"));
                response.setProductCate(rs.getInt("productCate"));
                response.setReviewCnt(rs.getInt("reviewCnt"));
                response.setProductCom(rs.getString("productCom"));
                response.setReviewAvg(rs.getFloat("reviewAvg"));
                response.setThumbnail(rs.getString("thumbnail"));

                return response;
            }
        },search);
    }


    // 상품 카테고리별 리스트 가져오기
    public List<GetProductResponse> getProductListOrderByCate(int cate){
        String query= "select p.productNum, p.productName, p.productPrice, p.productCate,p.productCom, (select count(r.reviewNum) from review r where r.productNum=p.productNum) as reviewCnt, (select avg(r.pointAvg) from review r where r.productNum=p.productNum) as reviewAvg,(select t.thumbnail from productThumbnails t where t.productNum=p.productNum limit 1) as thumbnail from product p where p.status='active' and p.productCate=?";
        GetProductResponse response= new GetProductResponse();
        return this.jdbcTemplate.query(query, new RowMapper<GetProductResponse>() {
            @Override
            public GetProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProductResponse response= new GetProductResponse();
                response.setProductNum(rs.getInt("productNum"));
                response.setProductName(rs.getString("productName"));
                response.setProductPrice(rs.getInt("productPrice"));
                response.setProductCate(rs.getInt("productCate"));
                response.setReviewCnt(rs.getInt("reviewCnt"));
                response.setProductCom(rs.getString("productCom"));
                response.setReviewAvg(rs.getFloat("reviewAvg"));
                response.setThumbnail(rs.getString("thumbnail"));

                return response;
            }
        },cate);
    }

    // 상품 카테고리별 검색 리스트 가져오기
    public List<GetProductResponse> getProductListOrderByCateAndSearch(int cate,String search){
        String query= "select p.productNum, p.productName, p.productPrice, p.productCate,p.productCom, (select count(r.reviewNum) from review r where r.productNum=p.productNum) as reviewCnt, (select avg(r.pointAvg) from review r where r.productNum=p.productNum) as reviewAvg,(select t.thumbnail from productThumbnails t where t.productNum=p.productNum limit 1) as thumbnail from product p where p.status='active' and p.productCate=? and p.productName like ?";
        Object[] selectParam= new Object[]{cate,search};
        GetProductResponse response= new GetProductResponse();
        return this.jdbcTemplate.query(query, new RowMapper<GetProductResponse>() {
            @Override
            public GetProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetProductResponse response= new GetProductResponse();
                response.setProductNum(rs.getInt("productNum"));
                response.setProductName(rs.getString("productName"));
                response.setProductPrice(rs.getInt("productPrice"));
                response.setProductCate(rs.getInt("productCate"));
                response.setReviewCnt(rs.getInt("reviewCnt"));
                response.setProductCom(rs.getString("productCom"));
                response.setReviewAvg(rs.getFloat("reviewAvg"));
                response.setThumbnail(rs.getString("thumbnail"));

                return response;
            }
        },selectParam);
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


    // 상품 주문시 상품 개수 줄이기
    public int reduceProductCnt(List<OrderMap> maps){
        String query="update product set productCnt=productCnt-? where productNum=?";
        int cnt=0;
        for(OrderMap map:maps){
            Object[] updateParam= new Object[]{map.getOrderCnt(),map.getProductNum()};
            if(this.jdbcTemplate.update(query,updateParam)==1) cnt++;
        }
        return cnt;
    }

    // 상품 스크랩 메서드
    public int productScrap(int userNum,int productNum){
        String query="insert into scrap (userNum,productNum) values(?,?)";
        Object[] insertParam=new Object[]{userNum,productNum};
        return this.jdbcTemplate.update(query,insertParam);
    }

    // 홈 화면 상품 리스트
    public List<GetHomeProduct> getHomeProducts(){
        String query="select p.productNum, p.productName, (select t.thumbnail from productThumbnails t where p.productNum=p.productNum limit 1) as thumbnail from product p  where p.status='active' order by productNum desc limit 4";
        return this.jdbcTemplate.query(query, new RowMapper<GetHomeProduct>() {
            @Override
            public GetHomeProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetHomeProduct res= new GetHomeProduct();
                res.setProductNum(rs.getInt("productNum"));
                res.setProductName(rs.getString("productName"));
                res.setProductThumbnail(rs.getString("thumbnail"));
                return res;
            }
        });
    }

    // 리뷰 수정시 기존 사진 삭제를 위한 사진 가져오기
    public String getReviewImg(int reviewNum){
        String query="select storedFilename from review where reviewNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("storedFilename");
            }
        },reviewNum);
    }












    // 테스트 데이터 작성 메서드
    public int postProduct(ExamSaveDTO req,List<String> filenames, List<String> savedThumbnails){
        int result=0;
        int productNum=0;
        String query="insert into product (productName,productPrice,productInfo,productCate,productCnt) values(?,?,?,?,?)";
        Object[] insertParam= new Object[]{req.getProductName(),req.getProductPrice(),req.getProductInfo(),req.getProductCate(),req.getProductCnt()};
        if(this.jdbcTemplate.update(query,insertParam)==1){
            String query1="select last_insert_id() as productNum";
            productNum=this.jdbcTemplate.queryForObject(query1, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt("productNum");
                }
            });
            this.postProductPics(filenames,productNum);
            this.postProductThumbnails(savedThumbnails,productNum);
            result=1;
        }
        return result;

    }

    public void postProductThumbnails(List<String> thumbnails,int productNum){
        String query="insert into productThumbnails (thumbnail,productNum) values(?,?)";
        for(String thumbnail:thumbnails){
            Object[] insertThumbnailParam= new Object[]{thumbnail,productNum};
            this.jdbcTemplate.update(query,insertThumbnailParam);
        }
    }

    public void postProductPics(List<String> filenames,int productNum){
        String query="insert into productPics (storedFilename,productNum) values(?,?)";
        for(String filename:filenames){
            Object[] insertPicsParam= new Object[]{filename,productNum};
            this.jdbcTemplate.update(query,insertPicsParam);
        }
    }




}
