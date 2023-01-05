package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
public class PostContentRequest {
    @Min(value = 1)
    @Max(value = 9)
    @NotBlank
    @Positive
    private int contentCate;

    @Min(value = 0)
    @Max(value = 5)
    @NotBlank
    @Positive
    private int size;

    @Min(value = 0)
    @Max(value = 6)
    @NotBlank
    @Positive
    private int form;

    @Min(value = 0)
    @Max(value = 7)
    @NotBlank
    @Positive
    private int style;

    @Positive
    @NotBlank
    private int userNum;

    @NotBlank
    private String contentTitle;

    @Null
    private List<String> keywords;

    private String contentImg;

    @NotBlank
    private String contents;
}
