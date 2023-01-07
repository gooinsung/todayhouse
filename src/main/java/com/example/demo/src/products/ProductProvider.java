package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.GetHomeProduct;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.dto.GetReviewResponse;
import com.example.demo.src.products.dto.object.GetProduct;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProductProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;
    private S3Uploader s3Uploader;

    @Autowired
    public ProductProvider(ProductDao productDao,S3Uploader s3Uploader) {
        this.productDao = productDao;
        this.s3Uploader=s3Uploader;
    }

    @Transactional
    public List<GetProductResponse> getProductList() throws BaseException, IOException {
        try{
            List<GetProductResponse> getProducts = productDao.getProductList();
            return getProducts;
        }catch(Exception exception){
            logger.error("App - getProductList ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    @Transactional
    public GetProductDetailResponse getProductDetailResponse1(int productNum) throws BaseException,IOException{
        try{
            GetProductDetailResponse response= new GetProductDetailResponse();
            response.setProduct(productDao.getProductDetail(productNum));
            response.setProductImg(productDao.getProductImgs(productNum));
            response.setThumbnails(productDao.getThumbnails(productNum));
            return response;
        }catch (Exception exception){
            logger.error("App - getProductDetailResponse1 ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetReviewResponse> getReviews(int productNum) throws BaseException,IOException{
        try{
            return productDao.getReviews(productNum);
        }catch (Exception exception){
            logger.error("App - getReviews ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public GetReviewResponse getReview(int reviewNum) throws BaseException,IOException{
        try{
            return productDao.getReview(reviewNum);
        }catch (Exception exception){
            logger.error("App - getReview ProductProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

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
