package com.example.demo.src.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetScrapResponse {
    private String type;
    private int number;
    private String thumbnail;
}

