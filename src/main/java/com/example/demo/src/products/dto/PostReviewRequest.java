package com.example.demo.src.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReviewRequest {
    private int userNum;
    private String reviewContent;
    private int point1;
    private int point2;
    private int point3;
    private int point4;
    private int productNum;

}
