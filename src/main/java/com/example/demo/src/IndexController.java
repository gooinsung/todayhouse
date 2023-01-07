package com.example.demo.src;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.UserService;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.utils.JwtProvider;
import com.example.demo.utils.S3Uploader;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.coyote.Response;
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


    private S3Uploader s3Uploader;
    private UserService userService;

    private JwtProvider jwtProvider;

    @Autowired
    public IndexController(S3Uploader s3Uploader, UserService userService, JwtProvider jwtProvider) {
        this.s3Uploader = s3Uploader;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @GetMapping("")
    public BaseResponse<String> home(){
        return new BaseResponse<>("IndexPage");
    }

    // 회원 가입 API(17)
    @PostMapping("/join")
    public BaseResponse<String> join(@Validated @RequestBody PostJoinRequest postJoinRequest) throws BaseException {
        String result="회원 가입 실패";
        if(userService.createUser(postJoinRequest)){
            result="회원 가입 성공";
        }
        return new BaseResponse<>(result);
    }

    // 로그인 API(18)
    @PostMapping("/login")
    public /*ResponseEntity<String>*/BaseResponse<String> login(@Validated @RequestBody PostLoginRequest postLoginRequest, HttpServletResponse response) throws BaseException{
        String result="로그인 실패";
        if(userService.loginUser(postLoginRequest)){
            result="로그인 성공";
            String jwt= jwtProvider.createJwt(postLoginRequest.getUserEmail());
            response.setHeader("jwt",jwt);
        }
        return new BaseResponse<>(result);
    }

/*    // 스크랩 API(24)
    @PostMapping("")
    public String scrap(@RequestParam int userNum, @RequestParam(required = false) int contentNum, @RequestPart(required = false) int productNum) throws BaseException{
        if(contentNum!=0){

        }
    }*/
}
