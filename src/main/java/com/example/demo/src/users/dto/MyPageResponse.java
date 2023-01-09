package com.example.demo.src.users.dto;

import com.example.demo.src.users.dto.object.MyPageContent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageResponse {
    private int userNum;
    private String userNickName;
    private String userImg;
    private int scrapCnt;
    private List<MyPageContent> contents;
    private List<GetScrapResponse> scraps;
}
