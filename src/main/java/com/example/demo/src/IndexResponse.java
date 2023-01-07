package com.example.demo.src;

import com.example.demo.src.contents.dto.GetHomeContent;
import com.example.demo.src.contents.dto.GetHomeFamousContent;
import com.example.demo.src.products.dto.GetHomeProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IndexResponse {
    private List<GetHomeProduct> products;
    private List<GetHomeContent> contents;
    private List<GetHomeFamousContent> famousContents;
}
