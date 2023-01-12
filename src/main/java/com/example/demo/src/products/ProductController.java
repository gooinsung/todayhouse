package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.products.dto.*;
import com.example.demo.utils.S3Uploader;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.executable.ValidateOnExecution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductService productService;

    private ProductProvider productProvider;
    private S3Uploader s3Uploader;

    @Autowired
    public ProductController(ProductService productService, ProductProvider productProvider,S3Uploader s3Uploader) {
        this.productService = productService;
        this.productProvider = productProvider;
        this.s3Uploader = s3Uploader;
    }

    // 상품 상세조회 API(1)
    @GetMapping("/{productNum}")
    public BaseResponse<GetProductDetailResponse> getProductDetails(@PathVariable int productNum) throws BaseException, IOException{
        logger.info("ProductController 내 1번 API 실행");
        GetProductDetailResponse response=productProvider.getProductDetailResponse(productNum);
        return new BaseResponse<GetProductDetailResponse>(response);
    }


    // 상품리스트 조회 API(2), 상품 검색 API(34)
    @GetMapping("")
    public BaseResponse<List<GetProductResponse>> getProductList(@RequestParam(required = false) String search) throws BaseException, IOException {
        List<GetProductResponse> response= new ArrayList<>();
        if(search==null){
            logger.info("ProductController 내 2번 API 실행");
            response= productProvider.getProductList();
        }else{
            logger.info("ProductController 내 34번 API 실행");
            response= productProvider.getProductListBySearch(search);
        }

        return new BaseResponse<>(response);
    }

    // 특정 상품 리뷰 조회 API(3)
    @GetMapping("/{productNum}/review")
    public BaseResponse<List<GetReviewResponse>> getProductReview(@PathVariable int productNum)throws BaseException, IOException{
        logger.info("ProductController 내 3번 API 실행");
        List<GetReviewResponse> result= productProvider.getReviews(productNum);
        return new BaseResponse<>(result);
    }

    // 상품 리뷰 작성 API(4)
    @PostMapping("/{productNum}/review/write")
    public BaseResponse<String> postReview(@PathVariable int productNum, @RequestPart @Validated PostReviewRequest req, @RequestPart MultipartFile file) throws BaseException,IOException{
        logger.info("ProductController 내 4번 API 실행");
        String message="리뷰 작성 실패";
        if(productService.postReview(req,file,productNum)){
            message="리뷰 작성 성공";
        }
        return new BaseResponse<>(message);
    }

    // 상품 리뷰 수정 API(5)
    @PatchMapping("/{productNum}/review/write")
    public BaseResponse<String> patchReview(@PathVariable int productNum,@RequestPart @Validated PatchReviewRequest req,@RequestPart(required = false) MultipartFile file,@RequestParam int reviewNum) throws BaseException, IOException{
        logger.info("ProductController 내 5번 API 실행");
        String result="리뷰 수정 실패";
        if(productService.patchReview(req,file,reviewNum)){
            result="리뷰 수정 성공";
        }
        return new BaseResponse<>(result);
    }

    // 리뷰 삭제 API(6)
    @DeleteMapping("/{productNum}/review/write")
    public BaseResponse<String> deleteReview(@PathVariable int productNum,@RequestParam int reviewNum) throws BaseException{
        logger.info("ProductController 내 6번 API 실행");
        String result="리뷰 삭제 실패";
        if(productService.deleteReview(reviewNum)){
            result=" 리뷰 삭제 성공";
        }
        return new BaseResponse<>(result);
    }

    // 특정 리뷰 조회 API(23)
    @GetMapping("/{productNum}/review/write")
    public BaseResponse<GetReviewResponse> getReview(@PathVariable int productNum,@RequestParam int reviewNum) throws BaseException,IOException{
        logger.info("ProductController 내 23번 API 실행");
        GetReviewResponse res= productProvider.getReview(reviewNum);
        return new BaseResponse<>(res);
    }

    // 상품 카테고리별 조회 API(35), 상품 카테고리별 검색 API(36)
    @GetMapping("/view")
    public BaseResponse<List<GetProductResponse>> getProductOrderByCateAndSearch(@RequestParam int cate,@RequestParam(required = false) String search)throws BaseException{
        List<GetProductResponse> result= new ArrayList<>();
        if(search==null){
            logger.info("ProductController 내 35번 API 실행");
        result=productProvider.getProductResponseOrderByCate(cate);
        }else{
            logger.info("ProductController 내 36번 API 실행");
            result=productProvider.getProductResponseOrderByCateAndSearch(cate,search);
        }
        return new BaseResponse<>(result);
    }



















    // 테스트 데이터 삽입을 위한 메서드(지울것)
    @PostMapping("")
    public BaseResponse<String> postProduct(@RequestPart ExamRequest examRequest, @RequestPart MultipartFile[] thumbnails, @RequestPart MultipartFile[] files)throws BaseException,IOException{
        String result="실패";
        if(productService.postProduct(examRequest,thumbnails,files)){
            result="성공!";
        }
        return new BaseResponse<>(result);
    }



}
