package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.utils.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private UserProvider userProvider;
    private UserService userService;
    private JwtProvider jwtProvider;

    @Autowired
    public UserController(UserProvider userProvider, UserService userService,JwtProvider jwtProvider) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtProvider=jwtProvider;
    }
/*
    // 회원가입
    @PostMapping("/join")
    public BaseResponse<String> join(@Validated @RequestBody PostJoinRequest postJoinRequest) throws BaseException{
        String result="회원 가입 실패";
        if(userService.createUser(postJoinRequest)){
            result="회원 가입 성공";
        }
        return new BaseResponse<>(result);
    }*/

/*    // 로그인
    @PostMapping("/login")
    public BaseResponse<String> login(@Validated @RequestBody PostLoginRequest postLoginRequest, HttpServletResponse response) throws BaseException{
        String result="로그인 실패";
        if(userService.loginUser(postLoginRequest)){
            result="로그인 성공";
            String jwt= jwtProvider.createJwt(postLoginRequest.getUserEmail());
            response.setHeader("jwt",jwt);
        }
        return new BaseResponse<>(result);
    }*/

 /*   // 로그인
    @PostMapping("/login")
    public *//*ResponseEntity<String>*//*BaseResponse<String> login(@Validated @RequestBody PostLoginRequest postLoginRequest, HttpServletResponse response) throws BaseException{
        String result="로그인 실패";
        if(userService.loginUser(postLoginRequest)){
            result="로그인 성공";
            String jwt= jwtProvider.createJwt(postLoginRequest.getUserEmail());
            response.setHeader("jwt",jwt);
        }
        return new BaseResponse<>(result);

    }*/



}
