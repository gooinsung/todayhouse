package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.model.GetProduct;
import com.example.demo.utils.S3Uploader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            List<GetProduct> getProducts = productDao.getProductList();
            List<GetProductResponse> result = new ArrayList<>();
            for (GetProduct getProduct : getProducts) {
                GetProductResponse response = new GetProductResponse(getProduct);
                result.add(response);
            }
            return result;
        }catch(Exception exception){
            logger.error("App - getProductList Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    @Transactional
    public GetProductDetailResponse getProductDetailResponse1(int productNum) throws BaseException,IOException{
        try{
            GetProductDetailResponse response= new GetProductDetailResponse();
            response.setProduct(productDao.getProductDetail(productNum));
            response.setUrls(productDao.getProductImgs(productNum));
            return response;
        }catch (Exception exception){
            logger.error("App - getProductDetailResponse1 Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
