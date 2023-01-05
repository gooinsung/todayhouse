package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetContentResponse {
    private int contentNum;
    private String contentImg;
    private String contentTitle;
    private String userNickName;
}
