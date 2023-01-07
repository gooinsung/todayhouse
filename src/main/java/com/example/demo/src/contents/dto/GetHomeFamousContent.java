package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetHomeFamousContent {
    private int contentNum;
    private String contentImg;
    private String userNickName;
}
