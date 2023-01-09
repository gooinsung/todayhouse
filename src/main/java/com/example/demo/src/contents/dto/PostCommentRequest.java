package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class PostCommentRequest {

    @Positive
    private int contentNum;

    @Positive
    private int userNum;

    @NotNull
    private String comment;
}
