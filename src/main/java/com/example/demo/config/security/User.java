package com.example.demo.config.security;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class User {
    private int userNum;
    private String userEmail;
    private String userPw;
    private String userNickName;
    private String userImg;
}
