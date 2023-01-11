package com.example.demo.src.users.dto.object;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLoginResponse {
    private String jwt;
    private int userNum;
}
