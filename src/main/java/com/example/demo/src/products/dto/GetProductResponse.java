package com.example.demo.src.products.dto;

import com.example.demo.src.products.dto.object.GetProduct;
import lombok.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@NoArgsConstructor
public class GetProductResponse {
    private int productNum;
    private String productName;
    private int productPrice;
    private byte[] thumbnail;
    private int productCate;

    @Builder
    public GetProductResponse(GetProduct product) throws IOException {
        this.productNum=product.getProductNum();
        this.productName=product.getProductName();
        this.productPrice=product.getProductPrice();
        this.productCate=product.getProductCate();
        String path=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\";
        InputStream imageStream= new FileInputStream(path+product.getThumbnail());
        this.thumbnail= imageStream.readAllBytes();
    }
}
