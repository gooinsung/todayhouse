package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class GetContentComment {
    private int commentNum;
    private int userNum;
    private String userNickName;
    private String userImg;
    private Timestamp updatedAt;
    private String comments;
    private List<GetContentComment> childComments;
}
