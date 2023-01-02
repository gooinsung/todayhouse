package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.model.GetProduct;
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

@Service
public class ProductProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;

    @Autowired
    public ProductProvider(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public GetProductDetailResponse getProductDetailResponse(int productNum) throws BaseException, IOException {
        GetProductDetailResponse response= new GetProductDetailResponse();
        String path=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\";
        String folder="";
        List<Resource> resourceList= new ArrayList<>();
        List<byte[]> bytes= new ArrayList<>();
        List<String> imgNames= productDao.getProductImgs(productNum);
        List<Resource> resourceList1= new ArrayList<>();

        for(String img:imgNames){
    /*        Resource resource= new FileSystemResource(path+folder+img);
            resourceList.add(resource);*/
            Resource resource= new UrlResource("file:"+path+img);
            resourceList1.add(resource);
      /*      InputStream imageStream= new FileInputStream(path+img);
            byte[] imageByteArray=imageStream.readAllBytes();
            bytes.add(imageByteArray);*/
        }
        response.setProduct(productDao.getProductDetail(productNum));
        response.setByteArrays(bytes);
        response.setResourceList(resourceList1);
        /*response.setResourceList(resourceList);*/
        return response;

    }

    @Transactional
    public List<GetProductResponse> getProductList() throws BaseException, IOException {
       List<GetProduct> getProducts=productDao.getProductList();
       List<GetProductResponse> result= new ArrayList<>();
       for (GetProduct getProduct:getProducts){
            GetProductResponse response=new GetProductResponse(getProduct);
            result.add(response);
       }
       return result;
    }
}
