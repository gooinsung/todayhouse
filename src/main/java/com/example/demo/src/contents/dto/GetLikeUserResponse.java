package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLikeUserResponse {
    private int userNum;
    private String userNickName;
    private String userImg;
}
