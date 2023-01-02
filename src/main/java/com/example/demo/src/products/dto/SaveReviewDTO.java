package com.example.demo.src.products.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReviewDTO {
    private int userNum;
    private String reviewContent;
    private int point1;
    private int point2;
    private int point3;
    private int point4;
    private int productNum;
    private String filename;

    @Builder
    public SaveReviewDTO(PostReviewRequest request, String filename){
        this.userNum=request.getUserNum();
        this.reviewContent=request.getReviewContent();
        this.point1=request.getPoint1();
        this.point2=request.getPoint2();
        this.point3= request.getPoint3();
        this.point4=request.getPoint4();
        this.productNum=request.getProductNum();
        this.filename=filename;
    }
}
