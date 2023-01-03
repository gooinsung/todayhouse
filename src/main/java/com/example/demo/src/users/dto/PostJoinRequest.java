package com.example.demo.src.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class PostJoinRequest {
    
    @Email
    @NotBlank
    private String userEmail;
    
    @NotBlank
    private String userPw;
    
    @NotBlank
    private String userNickName;
    
    // 14세이상 약관동의

    @PositiveOrZero
    @Min(value=1)
    @Max(value=1)
    private int agree1;
    
    // 이용약관 동의

    @PositiveOrZero
    @Min(value=1)
    @Max(value=1)
    private int agree2;
    
    // 개인정보 이용 동의

    @PositiveOrZero
    @Min(value=1)
    @Max(value=1)
    private int agree3;
    
    // 마케팅 활용 동의

    @PositiveOrZero
    @Max(value=1)
    private int agree4;
    
    // 메세지 수신 동의

    @PositiveOrZero
    @Max(value=1)
    private int agree5;
    
}
