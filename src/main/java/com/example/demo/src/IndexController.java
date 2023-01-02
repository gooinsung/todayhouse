package com.example.demo.src;

import com.example.demo.config.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


    @GetMapping("")
    public BaseResponse<String> home(){
        return new BaseResponse<>("홈 페이지");
    }
}
