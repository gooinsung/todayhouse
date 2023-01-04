package com.example.demo.src.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class GetReviewResponse {
    private int reviewNum;
    private String userNickName;
    private int point1;
    private int point2;
    private int point3;
    private int point4;
    private float pointAvg;
    private Timestamp createdAt;
    private String reviewImg;
    private String reviewContent;
}
