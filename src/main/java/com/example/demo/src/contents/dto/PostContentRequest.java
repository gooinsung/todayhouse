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
    @Positive
    private int contentCate;

    @Min(value = 0)
    @Max(value = 5)
    @PositiveOrZero
    private int size;

    @Min(value = 0)
    @Max(value = 6)
    @PositiveOrZero
    private int form;

    @Min(value = 0)
    @Max(value = 7)
    @PositiveOrZero
    private int style;

    @Positive
    private int userNum;

    @NotBlank
    private String contentTitle;

    private List<String> keywords;

    private String contentImg;

    @NotBlank
    private String contents;
}
