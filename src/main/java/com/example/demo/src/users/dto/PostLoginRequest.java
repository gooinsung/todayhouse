package com.example.demo.src.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PostLoginRequest {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPw;
}
