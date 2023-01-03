package com.example.demo.src.products.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class PostReviewRequest {
    @Positive
    private int userNum;

    private String reviewContent;

    @PositiveOrZero
    @Max(value=5)
    private int point1;

    @PositiveOrZero
    @Max(value=5)
    private int point2;

    @PositiveOrZero
    @Max(value=5)
    private int point3;

    @PositiveOrZero
    @Max(value=5)
    private int point4;

}
