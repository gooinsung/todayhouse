package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
public class PatchContentRequest {
    @Min(value = 1)
    @Max(value = 9)
    @Positive
    private int contentCate;

    @Min(value = 0)
    @Max(value = 5)
    @Positive
    private int size;

    @Min(value = 0)
    @Max(value = 6)
    @Positive
    private int form;

    @Min(value = 0)
    @Max(value = 7)
    @Positive
    private int style;


    private String contentTitle;

    private List<String> keywords;

    private String contentImg;

    private String contents;
}
