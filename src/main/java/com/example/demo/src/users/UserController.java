package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.dto.GetScrapResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        List<GetScrapResponse> result= userService.getScraps(userNum);
        return new BaseResponse<>(result);
    }



}
