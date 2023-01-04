package com.example.demo.src.products.dto.object;

import com.example.demo.src.products.dto.PatchReviewRequest;
import com.example.demo.src.products.dto.PostReviewRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateReviewDTO {
    private int reviewNum;
    private String reviewContent;
    private int point1;
    private int point2;
    private int point3;
    private int point4;
    private String filename;
    private int pointAvg;

    @Builder
    public UpdateReviewDTO(PatchReviewRequest request, String filename, int reviewNum){
        this.reviewNum=reviewNum;
        this.reviewContent=request.getReviewContent();
        this.point1=request.getPoint1();
        this.point2=request.getPoint2();
        this.point3= request.getPoint3();
        this.point4=request.getPoint4();
        this.filename=filename;
        this.pointAvg= (request.getPoint1()+request.getPoint2()+ request.getPoint3()+ request.getPoint4())/4;
    }
}
