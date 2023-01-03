package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.products.dto.ExamRequest;
import com.example.demo.src.products.dto.GetProductDetailResponse;
import com.example.demo.src.products.dto.GetProductResponse;
import com.example.demo.src.products.dto.PostReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    // 상품 상세조회 페이지(1)
    @GetMapping("/{productNum}")
    public BaseResponse<GetProductDetailResponse> getProductDetails(@PathVariable int productNum) throws BaseException, IOException{
        GetProductDetailResponse response=productProvider.getProductDetailResponse1(productNum);
        return new BaseResponse<GetProductDetailResponse>(response);
    }


    // 상품리스트 조회 페이지(2)
    @GetMapping("")
    public BaseResponse<List<GetProductResponse>> getProductList() throws BaseException, IOException {
        List<GetProductResponse> result= productProvider.getProductList();
        return new BaseResponse<>(result);
    }


















    // 테스트 데이터 삽입을 위한 메서드(지울것)
    @PostMapping("")
    public BaseResponse<String> postProduct(@RequestPart ExamRequest examRequest, @RequestPart MultipartFile thumbnail, @RequestPart MultipartFile[] files)throws BaseException,IOException{
        String result="실패";
        if(productService.postProduct(examRequest,thumbnail,files)){
            result="성공!";
        }
        return new BaseResponse<>(result);
    }

    @PostMapping("/{productNum}/review/write")
    public BaseResponse<String> postReview(@PathVariable int productNum,@RequestPart PostReviewRequest req,@RequestPart MultipartFile file) throws BaseException,IOException{
        String message="리뷰 작성 실패";
        if(productService.postReview(req,file,productNum)){
            message="리뷰 작성 성공";
        }
        return new BaseResponse<>(message);
    }


}
