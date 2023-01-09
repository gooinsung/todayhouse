package com.example.demo.src;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.contents.ContentProvider;
import com.example.demo.src.products.ProductProvider;
import com.example.demo.src.users.UserService;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.utils.JwtProvider;
import com.example.demo.utils.S3Uploader;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class IndexController {

    final Logger logger= LoggerFactory.getLogger(this.getClass());
    private S3Uploader s3Uploader;
    private ProductProvider productProvider;
    private ContentProvider contentProvider;
    private UserService userService;

    private JwtProvider jwtProvider;
    @Autowired
    public IndexController(S3Uploader s3Uploader, ProductProvider productProvider, ContentProvider contentProvider, UserService userService, JwtProvider jwtProvider) {
        this.s3Uploader = s3Uploader;
        this.productProvider = productProvider;
        this.contentProvider = contentProvider;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }



    // 홈 화면 (19)API
    @GetMapping("")
    public BaseResponse<IndexResponse> home() throws BaseException{
        logger.info("IndexController 내 19번 API 실행");
        IndexResponse result= new IndexResponse();
        result.setContents(contentProvider.getHomeContents());
        result.setProducts(productProvider.getHomeProducts());
        result.setFamousContents(contentProvider.getHomeFamousContents());
        return new BaseResponse<>(result);
    }

    // 회원 가입 API(17)
    @PostMapping("/join")
    public BaseResponse<String> join(@Validated @RequestBody PostJoinRequest postJoinRequest) throws BaseException {
        logger.info("IndexController 내 17번 API 실행");
        String result="회원 가입 실패";
        if(userService.createUser(postJoinRequest)){
            result="회원 가입 성공";
        }
        return new BaseResponse<>(result);
    }

    // 로그인 API(18)
    @PostMapping("/login")
    public BaseResponse<String> login(@Validated @RequestBody PostLoginRequest postLoginRequest, HttpServletResponse response) throws BaseException{
        logger.info("IndexController 내 18번 API 실행");
        String result="로그인 실패";
        if(userService.loginUser(postLoginRequest)){
            result="로그인 성공";
            String jwt= jwtProvider.createJwt(postLoginRequest.getUserEmail());
            response.setHeader("jwt",jwt);
        }
        return new BaseResponse<>(result);
    }

    // 스크랩 API(24)
    @PostMapping("")
    public BaseResponse<String> scrap(@RequestParam int userNum, @RequestParam String type, @RequestParam int number) throws BaseException{
        logger.info("IndexController 내 24번 API 실행");
        String result="스크랩북 추가 실패";
        if(userService.addScrap(userNum,type,number)){
            result="스크랩북 추가 성공";
        }
        return new BaseResponse<>(result);
    }
}
