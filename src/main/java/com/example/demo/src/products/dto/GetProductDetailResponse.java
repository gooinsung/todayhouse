package com.example.demo.src.products.dto;

import com.example.demo.src.products.model.Product;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDetailResponse {
    private Product product;
/*    private List<Resource> resourceList= new ArrayList<>();*/
    private List<byte[]> byteArrays= new ArrayList<>();
    private List<Resource> resourceList= new ArrayList<>();


}
