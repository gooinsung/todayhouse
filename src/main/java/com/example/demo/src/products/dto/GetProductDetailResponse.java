package com.example.demo.src.products.dto;

import com.example.demo.src.products.dto.object.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetProductDetailResponse {
    private Product product;
    private List<String> productImg= new ArrayList<>();
}
