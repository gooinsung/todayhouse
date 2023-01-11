package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.GetHomeProduct;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.dto.GetReviewResponse;
import com.example.demo.src.products.dto.object.GetProduct;
import com.example.demo.src.users.UserDao;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProductProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;
    private S3Uploader s3Uploader;
    private UserDao userDao;

    @Autowired
    public ProductProvider(ProductDao productDao,S3Uploader s3Uploader,UserDao userDao) {
        this.productDao = productDao;
        this.s3Uploader=s3Uploader;
        this.userDao=userDao;
    }

    // 상품 조회 메서드
    @Transactional
    public List<GetProductResponse> getProductList() throws BaseException {
        try{
            List<GetProductResponse> getProducts = productDao.getProductList();
            return getProducts;
        }catch(Exception exception){
            logger.error("App - getProductList ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // 상품 검색 메서드
    @Transactional
    public List<GetProductResponse> getProductListBySearch(String search) throws BaseException{
        try{
            String searchWord="%"+search+"%";
            List<GetProductResponse> getProductsBySearch=productDao.getProductListBySearch(searchWord);
            return getProductsBySearch;
        }catch (Exception exception){
            logger.error("App - getProductListBySearch ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 카테고리별 조회 메서드
    @Transactional
    public List<GetProductResponse> getProductResponseOrderByCate(int cate) throws BaseException{
        try{
            return productDao.getProductListOrderByCate(cate);
        }catch (Exception exception){
            logger.error("App -     public List<GetProductResponse> getProductResponseOrderByCate(int cate) throws BaseException{\n ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 카테고리별 검색 메서드
    @Transactional
    public List<GetProductResponse> getProductResponseOrderByCateAndSearch(int cate,String search) throws BaseException{
        try{
            String searchWord="%"+search+"%";
            return productDao.getProductListOrderByCateAndSearch(cate,searchWord);
        }catch (Exception exception){
            logger.error("App -     public List<GetProductResponse> getProductResponseOrderByCate(int cate) throws BaseException{\n ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 상세정보 조회 메서드
    @Transactional
    public GetProductDetailResponse getProductDetailResponse(int productNum) throws BaseException,IOException{
        try{
            GetProductDetailResponse response= new GetProductDetailResponse();
            response.setProduct(productDao.getProductDetail(productNum));
            response.setProductImg(productDao.getProductImgs(productNum));
            response.setThumbnails(productDao.getThumbnails(productNum));
            return response;
        }catch (Exception exception){
            logger.error("App - getProductDetailResponse ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 리뷰 조회 메서드
    @Transactional
    public List<GetReviewResponse> getReviews(int productNum) throws BaseException,IOException{
        try{
            return productDao.getReviews(productNum);
        }catch (Exception exception){
            logger.error("App - getReviews ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 리뷰 조회 메서드
    @Transactional
    public GetReviewResponse getReview(int reviewNum) throws BaseException,IOException{
        try{
            return productDao.getReview(reviewNum);
        }catch (Exception exception){
            logger.error("App - getReview ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 홈 화면 상품 조회 메서드
    @Transactional
    public List<GetHomeProduct> getHomeProducts()throws BaseException{
        try{
            return productDao.getHomeProducts();
        }catch (Exception exception){
            logger.error("App - getHomeProducts ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
