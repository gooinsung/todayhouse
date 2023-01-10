package com.example.demo.src.contents.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PatchCommentRequest {

    @NotNull()
    private String comment;
}
