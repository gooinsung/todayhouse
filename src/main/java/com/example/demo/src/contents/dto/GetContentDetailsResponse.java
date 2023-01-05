package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class GetContentDetailsResponse {
    public int contentNum;
    private int contentCate;
    private int size;
    private int form;
    private int style;
    private String contentImg;
    private String contentTitle;
    private String contents;
    private Timestamp createdAt;
    private int userNum;
    private String userNickName;
    private String userImg;

}
