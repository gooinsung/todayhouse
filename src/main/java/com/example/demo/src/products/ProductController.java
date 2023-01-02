package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.dto.PostReviewRequest;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/products")
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductService productService;

    private ProductProvider productProvider;

    @Autowired
    public ProductController(ProductService productService, ProductProvider productProvider) {
        this.productService = productService;
        this.productProvider = productProvider;
    }

    @GetMapping("/{productNum}")
    public BaseResponse<GetProductDetailResponse> getProductDetails(@PathVariable int productNum) throws BaseException, IOException{
        GetProductDetailResponse response=productProvider.getProductDetailResponse(productNum);
        return new BaseResponse<GetProductDetailResponse>(response);
    }



    @GetMapping("")
    public BaseResponse<List<GetProductResponse>> getProductList() throws BaseException, IOException {
        List<GetProductResponse> result= productProvider.getProductList();
        return new BaseResponse<>(result);
    }
















    @PostMapping("/review/write")
    public BaseResponse<String> writeReview(@RequestPart PostReviewRequest req, @RequestPart MultipartFile file) throws BaseException, IOException {
        String message="리뷰 작성 실패";
        if(productService.postReview(req,file)){
            message="리뷰 작성 성공";
        }
        return new BaseResponse<>(message);
    }


}
