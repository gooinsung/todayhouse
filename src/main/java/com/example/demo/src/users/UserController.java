package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.security.PrincipalDetails;
import com.example.demo.src.contents.dto.GetContentComment;
import com.example.demo.src.products.dto.GetReviewResponse;
import com.example.demo.src.users.dto.GetScrapResponse;
import com.example.demo.src.users.dto.MyPageResponse;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.utils.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    // 스크랩북 조회 API(20)
    @GetMapping("/scrap/{userNum}")
    public BaseResponse<List<GetScrapResponse>> getScraps(@PathVariable int userNum) throws BaseException{
        logger.info("UserController 내 20번 API 실행");
        List<GetScrapResponse> result= userProvider.getScraps(userNum);
        return new BaseResponse<>(result);
    }

    // 마이페이지 조회 API(21)
    @GetMapping("/{userNum}")
    public BaseResponse<MyPageResponse> getMyPage(@PathVariable int userNum) throws BaseException{
        logger.info("UserController 내 21번 API 실행");
        MyPageResponse myPage= userProvider.getMyPage(userNum);
        return new BaseResponse<>(myPage);
    }

    // 마이페이지 유저정보 수정 API(31)
    @PatchMapping("/{userNum}/mypage")
    public BaseResponse<String> patchMyPage(@PathVariable int userNum,@RequestPart(required = false) String userNickName, @RequestPart(required = false) MultipartFile userImg) throws BaseException, IOException {
        logger.info("UserController 내 31번 API 실행");
        String result="유저 정보 수정 실패";
        if(userService.updateUser(userNum,userNickName,userImg)){
            result="유저 정보 수정 성공";
        }
        return new BaseResponse<>(result);
    }

    // 내가 쓴 리뷰 조회 API(32)
    @GetMapping("/{userNum}/myreview")
    public BaseResponse<List<GetReviewResponse>> getMyReviews(@PathVariable int userNum) throws BaseException{
        logger.info("UserController 내 32번 API 실행");
        List<GetReviewResponse> response=userProvider.getMyReviews(userNum);
        return new BaseResponse<>(response);
    }

    // 내가 쓴 댓글 조회 API(33)
    @GetMapping("/{userNum}/mycomment")
    public BaseResponse<List<GetContentComment>> getMyComments(@PathVariable int userNum) throws BaseException{
        logger.info("UserController 내 33번 API 실행");
        List<GetContentComment> response= userProvider.getMyComments(userNum);
        return new BaseResponse<>(response);
    }

}
