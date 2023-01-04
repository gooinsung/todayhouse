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
    
    // 마케딩 활용 동의
    @PositiveOrZero
    @Min(value=1)
    @Max(value=1)
    private int agree1;
    
    // 메세지 수신 동의
    @PositiveOrZero
    @Min(value=1)
    @Max(value=1)
    private int agree2;

}
